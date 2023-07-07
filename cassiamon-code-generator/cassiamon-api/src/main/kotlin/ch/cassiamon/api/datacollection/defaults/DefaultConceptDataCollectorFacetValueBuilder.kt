package ch.cassiamon.api.datacollection.defaults

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.datacollection.annotations.*

@DataCollector
interface DefaultConceptDataCollectorFacetValueBuilder {

    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): DefaultConceptDataCollectorFacetValueBuilder

    @CommitConcept
    fun attach()
}
