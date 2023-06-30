package ch.cassiamon.api.registration

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.annotations.datacollector.*
import ch.cassiamon.api.model.ConceptIdentifier

@DataCollector
interface InputSourceDataCollector {

    @AddConcept(clazz = InputSourceConceptFacetValueBuilder::class)
    fun newConceptData(
        @ConceptNameValue conceptName: ConceptName,
        @ConceptIdentifierValue conceptIdentifier: ConceptIdentifier,
        @ParentConceptIdentifierValue parentConceptIdentifier: ConceptIdentifier? = null): InputSourceConceptFacetValueBuilder

}
