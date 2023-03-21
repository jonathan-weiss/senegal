package ch.cassiamon.engine.model

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.model.validator.ModelConceptInputDataValidator
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodePool
import ch.cassiamon.pluginapi.model.exceptions.DuplicateConceptIdentifierFoundModelException
import ch.cassiamon.pluginapi.model.exceptions.DuplicateFacetNameFoundModelException

object ConceptModelGraphCalculator {

    fun calculateConceptModelGraph(schema: Schema, modelInputData: ModelInputData): ConceptModelGraph {
        val nodePool = MutableConceptModelNodePool()

        val calculationAndValidationData = CalculationAndValidationData(
            schema = schema,
            infiniteLoopDetector = InfiniteLoopDetector(),
            conceptModelNodePool = nodePool
        )

        modelInputData.entries.forEach { inputDataEntry ->

            checkForDuplicateConceptIdentifier(inputDataEntry, nodePool)
            checkForDuplicateFacetNames(inputDataEntry)
            ModelConceptInputDataValidator.validateSingleEntry(schema, inputDataEntry)

            val conceptModelNode = createConceptModelNode(inputDataEntry, calculationAndValidationData)
            nodePool.addConceptModelNode(conceptModelNode)
        }

        nodePool.allConceptModelNodes().forEach { checkConceptModelNodeBy(schema, it) }

        return ConceptModelGraphDefaultImpl(nodePool.allConceptModelNodes())
    }

    private fun checkConceptModelNodeBy(schema: Schema, conceptModelNode: ConceptModelNode) {
        val templateFacetSchemas = schema.conceptByConceptName(conceptModelNode.conceptName).templateFacets

        conceptModelNode.parent()
        conceptModelNode.allChildren()
        val templateFacetNames = conceptModelNode.templateFacetValues.allTemplateFacetNames()
        for(templateFacetName in templateFacetNames) {
            val templateFacetSchema = templateFacetSchemas.first { it.templateFacet.facetName == templateFacetName }
            conceptModelNode.templateFacetValues.facetValue(templateFacetSchema.templateFacet)
        }

    }


    private fun createConceptModelNode(
        inputDataEntry: ModelConceptInputDataEntry,
        calculationAndValidationData: CalculationAndValidationData,
    ): ConceptModelNode {

        // TODO Add MaterializedVersion if wanted
        return ReactiveConceptModelNode(
            conceptName = inputDataEntry.conceptName,
            conceptIdentifier = inputDataEntry.conceptIdentifier,
            parentConceptIdentifier = inputDataEntry.parentConceptIdentifier,
            inputFacetValues = inputDataEntry.inputFacetValueAccess,
            calculationAndValidationData = calculationAndValidationData
        )
    }

    private fun checkForDuplicateConceptIdentifier(
        inputDataEntry: ModelConceptInputDataEntry,
        nodePool: ConceptModelNodePool
    ) {
        if(nodePool.containsConcept(inputDataEntry.conceptIdentifier)) {
            throw DuplicateConceptIdentifierFoundModelException(inputDataEntry.conceptName, inputDataEntry.conceptIdentifier)
        }
    }

    private fun checkForDuplicateFacetNames(inputDataEntry: ModelConceptInputDataEntry) {
        val alreadyUsedFacetNames = mutableSetOf<FacetName>()

        inputDataEntry.inputFacetValueAccess.getFacetNames().forEach { facetName ->
            if (alreadyUsedFacetNames.contains(facetName)) {
                throw DuplicateFacetNameFoundModelException(inputDataEntry.conceptName, inputDataEntry.conceptIdentifier, facetName)
            }
            alreadyUsedFacetNames.add(facetName)
        }

    }
}
