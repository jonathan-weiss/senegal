package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName

interface Registration {

    fun newRootConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

}
