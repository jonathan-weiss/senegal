package ch.cassiamon.api.process.datacollection.defaults

import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.datacollection.annotations.*
import ch.cassiamon.api.process.schema.ConceptIdentifier

@DataCollector
interface DefaultDataCollectorConceptBuilder {

    @SetParent
    fun setParent(@ParentConceptIdentifierValue parentConceptIdentifier: ConceptIdentifier?): DefaultDataCollectorConceptBuilder

    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): DefaultDataCollectorConceptBuilder

}
