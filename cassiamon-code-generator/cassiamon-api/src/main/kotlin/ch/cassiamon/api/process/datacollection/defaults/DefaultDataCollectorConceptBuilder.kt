package ch.cassiamon.api.process.datacollection.defaults

import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.datacollection.annotations.*

@DataCollector
interface DefaultDataCollectorConceptBuilder {

    // TODO Add methods to change/set the parent concept
    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): DefaultDataCollectorConceptBuilder

}
