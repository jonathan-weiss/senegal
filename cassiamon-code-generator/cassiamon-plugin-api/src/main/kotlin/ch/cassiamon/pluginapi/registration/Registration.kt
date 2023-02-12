package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName

interface Registration {

    fun newRootConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun newChildConcept(conceptName: ConceptName, parentConceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))
    fun withExistingConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))
}
