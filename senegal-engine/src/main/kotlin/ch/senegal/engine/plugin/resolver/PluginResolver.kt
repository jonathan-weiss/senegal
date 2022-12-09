package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.*

object PluginResolver {

    fun resolvePlugins(plugins: Set<Plugin>): ResolvedPlugins {
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

        val allPurposeDecorEntries: List<ResolvedPurposeDecor> = purposes.values
            .flatMap { purpose -> purpose.purposeDecors
                .map { decor -> ResolvedPurposeDecor(
                    concept = allConcepts[decor.enclosingConceptName]
                        ?: throw IllegalArgumentException("Concept not " +
                                "found '${decor.enclosingConceptName.name}' for $decor."),
                    purpose = purpose,
                    purposeDecor = decor
                ) }  }

        val purposeDecorEntriesByConceptName: Map<ConceptName, List<ResolvedPurposeDecor>> = allPurposeDecorEntries
            .groupBy { it.concept.conceptName }

        val purposesByConceptName: Map<ConceptName, List<Purpose>> = allConcepts.values
            .flatMap { concept -> purposes.values
                .filter { purpose -> purpose.purposeDecors
                    .any { decor -> decor.enclosingConceptName == concept.conceptName  }
                }.map { Pair(concept.conceptName, it) }
            }.groupBy( keySelector = { it.first }, valueTransform = { it.second } )

        val allResolvedConcepts: Set<ResolvedConcept> = allConcepts.values
            .map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, purposeDecorEntriesByConceptName) }
            .toSet()
        return ResolvedPlugins(allResolvedConcepts)
    }

    private fun createConceptNode(
        concept: Concept,
        childConceptsByParentConcept: Map<ConceptName, List<Concept>>,
        purposesByConceptName: Map<ConceptName, List<Purpose>>,
        purposeDecorsByConceptName: Map<ConceptName, List<ResolvedPurposeDecor>>
    ): ResolvedConcept {
        return ResolvedConcept(
            concept = concept,
            enclosedPurposes = purposesByConceptName[concept.conceptName]?.toSet() ?: emptySet(),
            enclosedConcepts = childConceptsByParentConcept[concept.conceptName]
                ?.map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, purposeDecorsByConceptName) }
                ?.toSet() ?: emptySet(),
            enclosedPurposeDecors = purposeDecorsByConceptName[concept.conceptName] ?: emptyList()
        )
    }
}
