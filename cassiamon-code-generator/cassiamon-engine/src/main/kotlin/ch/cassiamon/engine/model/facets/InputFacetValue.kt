package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.model.facets.InputFacet


data class InputFacetValue<T>(
    val inputFacet: InputFacet<T>,
    val facetValue: T
)
