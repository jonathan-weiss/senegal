package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.FacetName

class FacetValueCollector {

    private val facets: MutableMap<FacetName, Any?> = mutableMapOf()

    fun addFacetValue(facetName: FacetName, facetValue: Any?){
        facets[facetName] = facetValue
    }


    fun getFacetNames(): Set<FacetName> {
        return facets.keys.toSet()
    }

    fun getFacetValues(): Map<FacetName, Any?> {
        return facets.toMap()
    }
}
