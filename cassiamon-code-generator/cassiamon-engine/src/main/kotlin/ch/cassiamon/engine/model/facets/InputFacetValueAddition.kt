package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.model.facets.MandatoryInputFacet
import ch.cassiamon.pluginapi.model.facets.OptionalInputFacet

interface InputFacetValueAddition {
    fun <T: Any> addFacetValue(facet: MandatoryInputFacet<T>, value: T)
    fun <T: Any?> addFacetValue(facet: OptionalInputFacet<T>, value: T)
}
