package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose

class ResolvedConcept(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedFacets: List<ResolvedFacet>,
    val enclosedConcepts: Set<ResolvedConcept>
) {

    fun getFacetByCombinedName(purposeFacetName: String): ResolvedFacet? {
        return enclosedFacets.firstOrNull { it.purposeFacetName == purposeFacetName }
    }
}
