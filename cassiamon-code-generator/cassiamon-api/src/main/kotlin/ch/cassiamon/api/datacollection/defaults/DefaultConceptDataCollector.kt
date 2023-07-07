package ch.cassiamon.api.datacollection.defaults

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.ConceptIdentifier
import ch.cassiamon.api.datacollection.annotations.*

@DataCollector
interface DefaultConceptDataCollector {

    @AddConcept(clazz = DefaultConceptDataCollectorFacetValueBuilder::class)
    fun newConceptData(
        @ConceptNameValue conceptName: ConceptName,
        @ConceptIdentifierValue conceptIdentifier: ConceptIdentifier,
        @ParentConceptIdentifierValue parentConceptIdentifier: ConceptIdentifier? = null): DefaultConceptDataCollectorFacetValueBuilder

}
