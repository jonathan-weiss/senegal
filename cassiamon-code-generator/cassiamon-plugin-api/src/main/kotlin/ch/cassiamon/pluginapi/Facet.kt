package ch.cassiamon.pluginapi

sealed class Facet(
    val facetName: ch.cassiamon.pluginapi.FacetName,
    val enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    val isOnlyCalculated: Boolean = false,
)
