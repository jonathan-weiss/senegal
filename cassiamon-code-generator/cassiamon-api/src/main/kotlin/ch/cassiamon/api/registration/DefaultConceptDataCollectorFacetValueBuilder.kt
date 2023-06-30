package ch.cassiamon.api.registration

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.datacollector.*

@DataCollector
interface DefaultConceptDataCollectorFacetValueBuilder {

    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): DefaultConceptDataCollectorFacetValueBuilder

    @CommitConcept
    fun attach()
}
