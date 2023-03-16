package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.facets.InputFacet

interface InputFacetValueAccess {

    fun <T> facetValue(facet: InputFacet<T>): T

    fun getFacetNames(): Set<FacetName>
}
