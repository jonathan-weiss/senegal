package ch.cassiamon.pluginapi.model.facets


data class InputFacetValue<T>(
    val inputFacet: InputFacet<T>,
    val facetValue: T
)
