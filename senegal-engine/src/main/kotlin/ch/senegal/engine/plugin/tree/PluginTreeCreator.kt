package ch.senegal.engine.plugin.tree

import ch.senegal.plugin.*

object PluginTreeCreator {

    fun createPluginTree(plugins: Set<Plugin>): PluginTree {
        val allConcepts: Map<ConceptName, Concept> = plugins
            .filterIsInstance<Concept>()
            .associateBy { it.conceptName }

        val childConceptsByParentConcept: Map<ConceptName, List<Concept>> = plugins
            .filterIsInstance<Concept>()
            .filter { it.enclosingConceptName != null }
            .groupBy { it.enclosingConceptName!! }

        val purposes: Map<PurposeName, Purpose> = plugins
            .filterIsInstance<Purpose>()
            .associateBy { it.purposeName }

        val allPurposeDecorEntries: List<PurposeDecorEntry> = purposes.values
            .flatMap { purpose -> purpose.purposeDecors
                .map { decor -> PurposeDecorEntry(
                    concept = allConcepts[decor.enclosingConceptName]
                        ?: throw IllegalArgumentException("Concept not " +
                                "found '${decor.enclosingConceptName.name}' for $decor."),
                    purpose = purpose,
                    purposeDecor = decor
                ) }  }

        val purposeDecorEntriesByConceptName: Map<ConceptName, List<PurposeDecorEntry>> = allPurposeDecorEntries
            .groupBy { it.concept.conceptName }

        val purposesByConceptName: Map<ConceptName, List<Purpose>> = allConcepts.values
            .flatMap { concept -> purposes.values
                .filter { purpose -> purpose.purposeDecors
                    .any { decor -> decor.enclosingConceptName == concept.conceptName  }
                }.map { Pair(concept.conceptName, it) }
            }.groupBy( keySelector = { it.first }, valueTransform = { it.second } )

        val allConceptNodes: Set<ConceptNode> = allConcepts.values
            .map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, purposeDecorEntriesByConceptName) }
            .toSet()
        return PluginTree(allConceptNodes)
    }

    private fun createConceptNode(
        concept: Concept,
        childConceptsByParentConcept: Map<ConceptName, List<Concept>>,
        purposesByConceptName: Map<ConceptName, List<Purpose>>,
        purposeDecorsByConceptName: Map<ConceptName, List<PurposeDecorEntry>>
    ): ConceptNode {
        return ConceptNode(
            concept = concept,
            enclosedPurposes = purposesByConceptName[concept.conceptName]?.toSet() ?: emptySet(),
            enclosedConcepts = childConceptsByParentConcept[concept.conceptName]
                ?.map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, purposeDecorsByConceptName) }
                ?.toSet() ?: emptySet(),
            enclosedPurposeDecors = purposeDecorsByConceptName[concept.conceptName] ?: emptyList()
        )
    }
}
