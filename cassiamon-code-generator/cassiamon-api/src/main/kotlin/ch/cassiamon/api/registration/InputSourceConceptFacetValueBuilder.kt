package ch.cassiamon.api.registration

import ch.cassiamon.api.model.facets.InputFacetValue

interface InputSourceConceptFacetValueBuilder {

    fun <T> addFacetValue(facetValue: InputFacetValue<T>): InputSourceConceptFacetValueBuilder

    fun attach()
}
