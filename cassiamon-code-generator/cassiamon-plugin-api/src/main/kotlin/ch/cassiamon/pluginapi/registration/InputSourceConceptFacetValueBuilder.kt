package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.model.facets.InputFacetValue

interface InputSourceConceptFacetValueBuilder {

    fun <T> addFacetValue(facetValue: InputFacetValue<T>): InputSourceConceptFacetValueBuilder

    fun attach()
}
