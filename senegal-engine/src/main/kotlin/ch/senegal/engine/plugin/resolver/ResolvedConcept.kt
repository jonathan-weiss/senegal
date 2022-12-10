package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.*

class ResolvedConcept(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedFacets: List<ResolvedFacet>,
    val enclosedConcepts: Set<ResolvedConcept>
) {

    fun getFacet(purposeName: PurposeName, facetName: FacetName): ResolvedFacet? {
        return enclosedFacets
            .filter { it.purpose.purposeName == purposeName }
            .firstOrNull { it.facet.facetName == facetName }
    }

    fun getFacetByCombinedName(purposeFacetName: PurposeFacetCombinedName): ResolvedFacet? {
        return enclosedFacets.firstOrNull { it.purposeFacetName == purposeFacetName }
    }
}
