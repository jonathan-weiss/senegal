package ch.cassiamon.api.registration

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.datacollector.*
import ch.cassiamon.api.model.facets.InputFacetValue

@DataCollector
interface InputSourceConceptFacetValueBuilder {

    @AddFacet
    fun addFacetValue(@FacetNameValue facetName: FacetName, @FacetValue facetValue: Any?): InputSourceConceptFacetValueBuilder

    @CommitConcept
    fun attach()
}
