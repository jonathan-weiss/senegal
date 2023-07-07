package ch.cassiamon.api.datacollection.defaults

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.annotations.datacollector.*
import ch.cassiamon.api.ConceptIdentifier

@DataCollector
interface DefaultConceptDataCollector {

    @AddConcept(clazz = DefaultConceptDataCollectorFacetValueBuilder::class)
    fun newConceptData(
        @ConceptNameValue conceptName: ConceptName,
        @ConceptIdentifierValue conceptIdentifier: ConceptIdentifier,
        @ParentConceptIdentifierValue parentConceptIdentifier: ConceptIdentifier? = null): DefaultConceptDataCollectorFacetValueBuilder

}
