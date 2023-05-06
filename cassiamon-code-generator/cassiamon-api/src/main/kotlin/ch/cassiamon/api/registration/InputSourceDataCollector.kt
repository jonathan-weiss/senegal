package ch.cassiamon.api.registration

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.ConceptIdentifier

interface InputSourceDataCollector {

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier? = null): InputSourceConceptFacetValueBuilder

}
