package ch.cassiamon.api.model

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.InputFacet

interface InputFacetValueAccess {

    fun <T> facetValue(facet: InputFacet<T>): T

    fun getFacetNames(): Set<FacetName>
}
