package ch.cassiamon.api.process.datacollection.defaults

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.datacollection.annotations.*

@DataCollector
interface DefaultConceptDataCollector {

    @AddConcept(clazz = DefaultConceptDataCollectorFacetValueBuilder::class)
    fun newConceptData(
        @ConceptNameValue conceptName: ConceptName,
        @ConceptIdentifierValue conceptIdentifier: ConceptIdentifier,
        @ParentConceptIdentifierValue parentConceptIdentifier: ConceptIdentifier? = null): DefaultConceptDataCollectorFacetValueBuilder

}
