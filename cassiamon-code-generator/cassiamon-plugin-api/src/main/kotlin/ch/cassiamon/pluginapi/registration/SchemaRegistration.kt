package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName

interface SchemaRegistration {

    fun newRootConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

}
