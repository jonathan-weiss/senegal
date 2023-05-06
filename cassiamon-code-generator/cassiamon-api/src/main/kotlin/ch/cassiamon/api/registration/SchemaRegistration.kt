package ch.cassiamon.api.registration

import ch.cassiamon.api.ConceptName

typealias SchemaRegistrationApi = (SchemaRegistration.() -> Unit) -> Unit
interface SchemaRegistration {

    fun newRootConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

}
