package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.*

class SchemaRegistrationDefaultImpl: SchemaRegistration, ConceptRegistration, SchemaProvider {
    private val committedConcepts: MutableSet<MutableConcept> = mutableSetOf()
    private val conceptsInCreationStack: MutableList<MutableConcept> = mutableListOf()

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

    override fun addTextFacet(
        facetName: NameOfMandatoryTextFacet
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManuelText(
            conceptName = concept.conceptName,
            facetName = facetName
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addTextFacet(
        facetName: NameOfOptionalTextFacet
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManuelText(
            conceptName = concept.conceptName,
            facetName = facetName
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedTextFacet(
        facetName: NameOfMandatoryTextFacet,
        calculationFunction: (ConceptModelNode) -> String
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedMandatoryTextFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedTextFacet(
        facetName: NameOfOptionalTextFacet,
        calculationFunction: (ConceptModelNode) -> String?
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedOptionalTextFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addIntegerNumberFacet(
        facetName: NameOfMandatoryIntegerNumberFacet
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManualIntegerNumber(
            conceptName = concept.conceptName,
            facetName = facetName,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addIntegerNumberFacet(
        facetName: NameOfOptionalIntegerNumberFacet
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManualIntegerNumber(
            conceptName = concept.conceptName,
            facetName = facetName,
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedIntegerNumberFacet(
        facetName: NameOfMandatoryIntegerNumberFacet,
        calculationFunction: (ConceptModelNode) -> Int
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedMandatoryIntegerNumber(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedIntegerNumberFacet(
        facetName: NameOfOptionalIntegerNumberFacet,
        calculationFunction: (ConceptModelNode) -> Int?
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedOptionalIntegerNumber(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addConceptReferenceFacet(
        facetName: NameOfMandatoryConceptReferenceFacet,
        referencedConcept: ConceptName,
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManualConceptReference(
            conceptName = concept.conceptName,
            facetName = facetName,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addConceptReferenceFacet(
        facetName: NameOfOptionalConceptReferenceFacet,
        referencedConcept: ConceptName,
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForManualConceptReference(
            conceptName = concept.conceptName,
            facetName = facetName,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedConceptReferenceFacet(
        facetName: NameOfMandatoryConceptReferenceFacet,
        referencedConcept: ConceptName,
        calculationFunction: (ConceptModelNode) -> ConceptIdentifier
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedMandatoryConceptReference(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedConceptReferenceFacet(
        facetName: NameOfOptionalConceptReferenceFacet,
        referencedConcept: ConceptName,
        calculationFunction: (ConceptModelNode) -> ConceptIdentifier?
    ) {
        val concept = currentConceptInCreation()
        val facet = FacetForCalculatedOptionalConceptReference(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetCalculationFunction = calculationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    private fun validateAndAttachFacet(concept: MutableConcept, facet: Facet) {
        if(facetExists(concept, facet.facetName)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.facetName)
        }

        concept.mutableFacets.add(facet)
    }

    private fun facetExists(concept: MutableConcept, facetName: FacetName): Boolean {
        return concept.mutableFacets.map { it.facetName }.contains(facetName)
    }

    private fun prepareConceptInCreation(conceptName: ConceptName): MutableConcept {

        val parentConceptName: ConceptName? = if(hasCurrentConceptInCreation()) currentConceptInCreation().conceptName else null
        val conceptInCreation = MutableConcept(
            conceptName = conceptName,
            parentConceptName = parentConceptName,
        )
        conceptsInCreationStack.add(conceptInCreation)
        return conceptInCreation
    }

    private fun currentConceptInCreation(): MutableConcept {
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
