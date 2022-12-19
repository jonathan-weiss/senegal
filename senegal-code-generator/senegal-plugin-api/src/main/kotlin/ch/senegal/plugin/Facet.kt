package ch.senegal.plugin

sealed class Facet(
    val facetName: FacetName,
    val enclosingConceptName: ConceptName,
    val isOnlyCalculated: Boolean = false,
)
