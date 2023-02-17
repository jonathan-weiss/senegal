package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.exceptions.*
import ch.cassiamon.pluginapi.registration.types.*

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
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        transformationFunction: TextFacetTransformationFunction?
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.TEXT,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = transformationFunction ?: NoOpTransformationFunctions.noOpTextTransformationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedTextFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: TextFacetCalculationFunction
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.TEXT,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        transformationFunction: IntegerNumberFacetTransformationFunction?
    ) {
        val concept = currentConceptInCreation()
        val facet = ManualFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.INTEGER_NUMBER,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = transformationFunction ?: NoOpTransformationFunctions.noOpIntegerNumberTransformationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: IntegerNumberFacetCalculationFunction
    ) {
        val concept = currentConceptInCreation()
        val facet = CalculatedFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.INTEGER_NUMBER,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName>
    ) {
        val concept = currentConceptInCreation()
        val facet = ConceptReferenceManualFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.CONCEPT_REFERENCE,
            facetDependencies = dependingOnFacets,
            facetTransformationFunction = NoOpTransformationFunctions.noOpConceptReferenceTransformationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    override fun addCalculatedConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName>,
        calculationFunction: ConceptReferenceFacetCalculationFunction
    ) {
        val concept = currentConceptInCreation()
        val facet = ConceptReferenceCalculatedFacet(
            conceptName = concept.conceptName,
            facetName = facetName,
            facetType = FacetType.CONCEPT_REFERENCE,
            facetDependencies = dependingOnFacets,
            facetCalculationFunction = calculationFunction,
            referencedConceptName = referencedConcept
        )
        validateAndAttachFacet(concept, facet)
    }

    private fun validateAndAttachFacet(concept: MutableConcept, facet: Facet) {
        if(facetExists(concept, facet.facetName)) {
            throw DuplicateFacetNameFoundSchemaException(facet.conceptName, facet.facetName)
        }

        val notFoundFacets = facet.facetDependencies.filter { !facetExists(concept, it) }.toSet()
        if(notFoundFacets.isNotEmpty()) {
            throw FacetDependencyNotFoundSchemaException(facet.conceptName, notFoundFacets)
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
