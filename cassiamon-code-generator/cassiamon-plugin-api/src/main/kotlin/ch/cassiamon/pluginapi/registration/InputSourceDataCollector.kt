package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier

interface InputSourceDataCollector {

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier? = null): InputSourceConceptFacetValueBuilder

}
