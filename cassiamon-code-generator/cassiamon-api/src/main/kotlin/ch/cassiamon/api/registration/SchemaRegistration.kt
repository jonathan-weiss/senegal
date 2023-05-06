package ch.cassiamon.api.registration

import ch.cassiamon.api.ConceptName

interface SchemaRegistration {

    fun newRootConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

}
