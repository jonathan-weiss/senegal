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

        val allFacetEntries: List<ResolvedFacet> = purposes.values
            .flatMap { purpose -> purpose.facets
                .map { facet -> ResolvedFacet(
                    concept = allConcepts[facet.enclosingConceptName]
                        ?: throw IllegalArgumentException("Concept not " +
                                "found '${facet.enclosingConceptName.name}' for facet $facet."),
                    purpose = purpose,
                    facet = facet
                ) }  }

        val facetsByConceptName: Map<ConceptName, List<ResolvedFacet>> = allFacetEntries
            .groupBy { it.concept.conceptName }

        val purposesByConceptName: Map<ConceptName, List<Purpose>> = allConcepts.values
            .flatMap { concept -> purposes.values
                .filter { purpose -> purpose.facets
                    .any { facet -> facet.enclosingConceptName == concept.conceptName  }
                }.map { Pair(concept.conceptName, it) }
            }.groupBy( keySelector = { it.first }, valueTransform = { it.second } )

        val allResolvedConcepts: Set<ResolvedConcept> = allConcepts.values
            .map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, facetsByConceptName) }
            .toSet()
        return ResolvedPlugins(allResolvedConcepts)
    }

    private fun createConceptNode(
        concept: Concept,
        childConceptsByParentConcept: Map<ConceptName, List<Concept>>,
        purposesByConceptName: Map<ConceptName, List<Purpose>>,
        facetsByConceptName: Map<ConceptName, List<ResolvedFacet>>
    ): ResolvedConcept {
        return ResolvedConcept(
            concept = concept,
            enclosedPurposes = purposesByConceptName[concept.conceptName]?.toSet() ?: emptySet(),
            enclosedConcepts = childConceptsByParentConcept[concept.conceptName]
                ?.map { createConceptNode(it, childConceptsByParentConcept, purposesByConceptName, facetsByConceptName) }
                ?.toSet() ?: emptySet(),
            enclosedFacets = facetsByConceptName[concept.conceptName] ?: emptyList()
        )
    }
}
