package ch.cassiamon.api.registration

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.InputFacetValue

interface InputSourceConceptFacetValueBuilder {

    fun <T> addFacetValue(facetValue: InputFacetValue<T>): InputSourceConceptFacetValueBuilder

    fun addFacetValue(facetName: FacetName, facetValue: Any?): InputSourceConceptFacetValueBuilder

    fun attach()
}
