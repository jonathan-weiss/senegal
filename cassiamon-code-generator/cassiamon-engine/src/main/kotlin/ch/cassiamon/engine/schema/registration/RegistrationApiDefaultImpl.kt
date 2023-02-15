package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.Facet
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.*

class RegistrationApiDefaultImpl: RegistrationApi, Registration, SchemaProvider {
    private val concepts: MutableSet<MutableConcept> = mutableSetOf()

    override fun provideSchema(): Schema {
        return Schema(concepts = concepts.associateBy { it.conceptName })
    }

    override fun configure(registration: Registration.() -> Unit) {
        registration(this)
    }

    override fun newRootConcept(conceptName: ConceptName, conceptRegistration: ConceptRegistration.() -> Unit) {
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = null
        )
        conceptRegistration(conceptTransaction)
        validateAndCommitConcept(conceptTransaction)
    }

    override fun newChildConcept(
        conceptName: ConceptName,
        parentConceptName: ConceptName,
        conceptRegistration: ConceptRegistration.() -> Unit
    ) {
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = parentConceptName
        )
        conceptRegistration(conceptTransaction)
        validateAndCommitConcept(conceptTransaction)
    }

    override fun withExistingConcept(
        conceptName: ConceptName,
        conceptRegistration: ConceptRegistration.() -> Unit
    ) {
        val conceptTransaction = MutableConcept(
            conceptName = conceptName,
            parentConceptName = null
        )
        conceptRegistration(conceptTransaction)
        validateAndCommitConcept(conceptTransaction)
    }

    private fun validateAndCommitConcept(concept: MutableConcept) {
        if(conceptExists(concept.conceptName)) {
            throw DuplicateConceptNameFoundSchemaException(concept.conceptName)
        }

        if(concept.parentConceptName != null) {
            if(concept.conceptName == concept.parentConceptName) {
                throw CircularConceptHierarchieFoundSchemaException(concept.conceptName, concept.parentConceptName)
            }
            if(!conceptExists(concept.parentConceptName)) {
                throw UnknownParentConceptFoundSchemaException(concept.conceptName, concept.parentConceptName)
            }
        }

        concepts.add(concept)
    }

    private fun conceptExists(conceptName: ConceptName): Boolean {
        return concepts.map { it.conceptName }.contains(conceptName)
    }



}
