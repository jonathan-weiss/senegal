package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.registration.*

class RegistrationApiDefaultImpl: RegistrationApi, Registration, SchemaProvider {
    private val concepts: MutableSet<MutableConcept> = mutableSetOf()

    override fun provideSchema(): Schema {
        return Schema(concepts = concepts.toList())
    }

    override fun configure(registration: Registration.() -> Unit) {
        registration(this)
    }

    override fun newRootConcept(conceptName: ConceptName, conceptRegistration: ConceptRegistration.() -> Unit) {
        println("newRootConcept $conceptName")
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = null
        )
        conceptRegistration(conceptTransaction)
        commitConcept(conceptTransaction)
    }

    override fun newChildConcept(
        conceptName: ConceptName,
        parentConceptName: ConceptName,
        conceptRegistration: ConceptRegistration.() -> Unit
    ) {
        println("newChildConcept $conceptName with parent $parentConceptName")
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = parentConceptName
        )
        conceptRegistration(conceptTransaction)
        commitConcept(conceptTransaction)
    }

    override fun withExistingConcept(
        conceptName: ConceptName,
        conceptRegistration: ConceptRegistration.() -> Unit
    ) {
        println("withExistingConcept $conceptName")
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = null
        )
        conceptRegistration(conceptTransaction)
        commitConcept(conceptTransaction)
    }

    private fun commitConcept(concept: MutableConcept) {
        println("Commit ${concept.conceptName} with parent ${concept.parentConceptName}")
        concepts.add(concept)
    }



}
