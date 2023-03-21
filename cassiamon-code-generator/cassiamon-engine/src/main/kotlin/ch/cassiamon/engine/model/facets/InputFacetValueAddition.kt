package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.model.facets.InputFacetValue

interface InputFacetValueAddition {
    fun <T> addFacetValue(facetWithValue: InputFacetValue<T>)
}
