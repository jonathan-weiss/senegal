package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.facets.CalculatedFacetSchema
import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.engine.schema.facets.ManualFacetSchema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.*

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

    override fun addFacet(
        facetDescriptor: ManualMandatoryTextFacetDescriptor
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: ManualOptionalTextFacetDescriptor
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedMandatoryTextFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> String
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedOptionalTextFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> String?
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedMandatoryIntegerNumberFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> Int
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedOptionalIntegerNumberFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> Int?
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor,
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor,
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacetSchema(
            conceptName = concept.conceptName,
            manualFacetDescriptor = facetDescriptor,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedMandatoryConceptReferenceFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> ConceptModelNode
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addFacet(
        facetDescriptor: CalculatedOptionalConceptReferenceFacetDescriptor,
        calculationFunction: (ConceptModelNode) -> ConceptModelNode?
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacetSchema(
            conceptName = concept.conceptName,
            calculatedFacetDescriptor = facetDescriptor,
            facetCalculationFunction = calculationFunction,
        )
        validateAndAttachFacet(concept, facet)
    }

    private fun validateAndAttachFacet(concept: MutableConceptSchema, facet: FacetSchema<*>) {
        if(facetExists(concept, facet.facetDescriptor)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.facetDescriptor.facetName)
        }

        concept.mutableFacets.add(facet)
    }

    private fun facetExists(concept: MutableConceptSchema, facetDescriptor: FacetDescriptor<*>): Boolean {
        return concept.hasFacet(facetDescriptor.facetName)
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
