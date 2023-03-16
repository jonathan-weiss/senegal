package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.model.facets.InputFacet

interface InputFacetValueAddition {
    fun <T> addFacetValue(facet: InputFacet<T>, value: T)
}
