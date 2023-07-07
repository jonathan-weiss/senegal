package ch.cassiamon.api.process.datacollection.defaults

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.datacollection.annotations.*
import ch.cassiamon.api.process.datacollection.annotations.*

@DataCollector
interface DefaultConceptDataCollectorFacetValueBuilder {

    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): DefaultConceptDataCollectorFacetValueBuilder

    @CommitConcept
    fun attach()
}
