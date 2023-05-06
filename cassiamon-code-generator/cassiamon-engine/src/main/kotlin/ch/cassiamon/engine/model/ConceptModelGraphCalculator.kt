package ch.cassiamon.engine.model

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.model.validator.CircularFacetDependencyDetector
import ch.cassiamon.engine.model.validator.ConceptModelNodeValidator
import ch.cassiamon.engine.model.validator.ModelConceptInputDataValidator
import ch.cassiamon.engine.domain.Schema
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
            circularFacetDependencyDetector = CircularFacetDependencyDetector(),
            conceptModelNodePool = nodePool
        )

        modelInputData.entries.forEach { inputDataEntry ->

            checkForDuplicateConceptIdentifier(inputDataEntry, nodePool)
            checkForDuplicateFacetNames(inputDataEntry)
            ModelConceptInputDataValidator.validateSingleEntry(schema, inputDataEntry)

            val conceptModelNode = createConceptModelNode(inputDataEntry, calculationAndValidationData)
            nodePool.addConceptModelNode(conceptModelNode)
        }


        // calculate and validate all facets of each conceptModelNode
        nodePool.allConceptModelNodes().forEach { conceptModelNode ->
            ConceptModelNodeValidator.validate(
                conceptModelNode = conceptModelNode,
                conceptSchema = schema.conceptByConceptName(conceptModelNode.conceptName),
            )
        }

        return ConceptModelGraphDefaultImpl(nodePool.allConceptModelNodes())
    }

    private fun createConceptModelNode(
        inputDataEntry: ModelConceptInputDataEntry,
        calculationAndValidationData: CalculationAndValidationData,
        useMaterializingConceptModelNodes: Boolean = true
    ): ConceptModelNode {

        val reactiveConceptModelNode = ReactiveConceptModelNode(
            conceptName = inputDataEntry.conceptName,
            conceptIdentifier = inputDataEntry.conceptIdentifier,
            parentConceptIdentifier = inputDataEntry.parentConceptIdentifier,
            inputFacetValues = inputDataEntry.inputFacetValueAccess,
            calculationAndValidationData = calculationAndValidationData
        )

        return if(useMaterializingConceptModelNodes) MaterializingConceptModelNode(reactiveConceptModelNode)
            else reactiveConceptModelNode
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
