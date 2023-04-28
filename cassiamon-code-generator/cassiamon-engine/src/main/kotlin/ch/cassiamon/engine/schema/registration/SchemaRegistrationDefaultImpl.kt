package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.facets.InputAndTemplateFacet
import ch.cassiamon.pluginapi.model.facets.InputFacet
import ch.cassiamon.pluginapi.model.facets.TemplateFacet
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.*
import ch.cassiamon.pluginapi.schema.InputFacetSchema
import ch.cassiamon.pluginapi.schema.TemplateFacetSchema

class SchemaRegistrationDefaultImpl: SchemaRegistration, ConceptRegistration, SchemaProvider {
    private val committedConcepts: MutableSet<MutableConceptSchema> = mutableSetOf()
    private val conceptsInCreationStack: MutableList<MutableConceptSchema> = mutableListOf()

    override fun provideSchema(): Schema {
        return Schema(concepts = committedConcepts.associateBy { it.conceptName })
    }

    override fun newRootConcept(conceptName: ConceptName, conceptRegistration: ConceptRegistration.() -> Unit) {
        if(hasCurrentConceptInCreation()) {
            throw CircularConceptHierarchieFoundSchemaException(conceptName, currentConceptInCreation().conceptName)
        }

        prepareConceptInCreation(conceptName)
        conceptRegistration(this)
        validateAndCommitConcept()
    }

    override fun newChildConcept(
        conceptName: ConceptName,
        conceptRegistration: ConceptRegistration.() -> Unit
    ) {
        prepareConceptInCreation(conceptName)
        conceptRegistration(this)
        validateAndCommitConcept()
    }

    override fun <T> addFacet(facet: InputFacet<T>) {
        val concept = currentConceptInCreation()
        val inputFacetSchema = InputFacetSchema(
            conceptName = concept.conceptName,
            inputFacet = facet
        )
        validateAndAttachInputFacet(concept, inputFacetSchema)
    }

    override fun <T> addFacet(facet: TemplateFacet<T>,
                              facetCalculationFunction: (ConceptModelNodeCalculationData) -> T) {
        val concept = currentConceptInCreation()
        val templateFacetSchema = TemplateFacetSchema(
            conceptName = concept.conceptName,
            templateFacet = facet,
            facetCalculationFunction = facetCalculationFunction,
        )
        validateAndAttachTemplateFacet(concept, templateFacetSchema)
    }

    override fun <I, O> addFacet(facet: InputAndTemplateFacet<I, O>) {
        val concept = currentConceptInCreation()
        val inputFacetSchema = InputFacetSchema(
            conceptName = concept.conceptName,
            inputFacet = facet
        )
        val templateFacetSchema = TemplateFacetSchema(
            conceptName = concept.conceptName,
            templateFacet = facet,
            facetCalculationFunction = facet.facetCalculationFunction,
        )
        validateAndAttachInputFacet(concept, inputFacetSchema)
        validateAndAttachTemplateFacet(concept, templateFacetSchema)
    }

    private fun validateAndAttachInputFacet(concept: MutableConceptSchema, facet: InputFacetSchema<*>) {
        if(concept.hasInputFacet(facet.inputFacet.facetName)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.inputFacet.facetName)
        }

        concept.mutableInputFacets.add(facet)
    }

    private fun validateAndAttachTemplateFacet(concept: MutableConceptSchema, facet: TemplateFacetSchema<*>) {
        if(concept.hasTemplateFacet(facet.templateFacet.facetName)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.templateFacet.facetName)
        }

        concept.mutableTemplateFacets.add(facet)
    }


    private fun prepareConceptInCreation(conceptName: ConceptName): MutableConceptSchema {

        val parentConceptName: ConceptName? = if(hasCurrentConceptInCreation()) currentConceptInCreation().conceptName else null
        val conceptInCreation = MutableConceptSchema(
            conceptName = conceptName,
            parentConceptName = parentConceptName,
        )
        conceptsInCreationStack.add(conceptInCreation)
        return conceptInCreation
    }

    private fun currentConceptInCreation(): MutableConceptSchema {
        return conceptsInCreationStack.last()
    }

    private fun hasCurrentConceptInCreation(): Boolean {
        return conceptsInCreationStack.isNotEmpty()
    }


    private fun validateAndCommitConcept() {
        val concept = conceptsInCreationStack.removeLast()
        if(conceptExistsInCommittedConcepts(concept.conceptName) || conceptExistsInCreationStack(concept.conceptName)) {
            throw DuplicateConceptNameFoundSchemaException(concept.conceptName)
        }

        if(concept.parentConceptName != null) {
            if(concept.conceptName == concept.parentConceptName) {
                throw CircularConceptHierarchieFoundSchemaException(concept.conceptName, concept.parentConceptName)
            }
            if(conceptExistsInCommittedConcepts(concept.parentConceptName)) {
                throw DuplicateConceptNameFoundSchemaException(concept.parentConceptName)
            }
            if(!conceptExistsInCreationStack(concept.parentConceptName)) {
                throw UnknownParentConceptFoundSchemaException(concept.conceptName, concept.parentConceptName)
            }
        }

        committedConcepts.add(concept)
    }

    private fun conceptExistsInCommittedConcepts(conceptName: ConceptName): Boolean {
        return committedConcepts.map { it.conceptName }.contains(conceptName)
    }

    private fun conceptExistsInCreationStack(conceptName: ConceptName): Boolean {
        return conceptsInCreationStack.map { it.conceptName }.contains(conceptName)
    }
}
