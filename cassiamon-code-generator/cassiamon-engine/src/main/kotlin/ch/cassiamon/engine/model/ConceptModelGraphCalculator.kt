package ch.cassiamon.engine.model

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.validator.ConceptModelNodeValidator
import ch.cassiamon.engine.schema.types.Concept
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.model.exceptions.DuplicateConceptIdentifierFoundModelException
import ch.cassiamon.pluginapi.model.exceptions.DuplicateFacetNameFoundModelException

object ConceptModelGraphCalculator {



    fun calculateConceptModelGraph(schema: Schema, modelInputData: ModelInputData): ConceptModelGraph {
        val infiniteLoopDetector = InfiniteLoopDetector()
        val nodePool = MutableConceptModelNodePool()

        modelInputData.entries.forEach { inputDataEntry ->

            checkForDuplicateConceptIdentifier(inputDataEntry, nodePool)
            checkForDuplicateFacetNames(inputDataEntry)
            ConceptModelNodeValidator.validateSingleEntry(schema, inputDataEntry)

            val manualFacetValues = inputDataEntry.facetValues.associate { Pair(it.facetName, it.facetValue) }
            val conceptModelNode = MaterializingConceptModelNode(
                schema = schema,
                infiniteLoopDetector = infiniteLoopDetector,
                nodePool = nodePool,
                conceptName = inputDataEntry.conceptName,
                conceptIdentifier = inputDataEntry.conceptIdentifier,
                manualFacetValues = manualFacetValues,
            )

            nodePool.addConceptModelNode(conceptModelNode)
        }


//        val nodePool: Map<ConceptIdentifier, MutableConceptModelNode> = createNodePool(schema, modelInputData)
//        val conceptIdentifiersByParentConceptIdentifier: Map<ConceptIdentifier, List<ConceptIdentifier>> = createConceptIdentifierByParent(modelInputData)
//
//
//        // linkTemplateNodes(calculatedModel)
//
//        val allConceptModelNodes: List<ConceptModelNode> = nodePool.values.toList()
//        val templateNodesByConcept: Map<ConceptName, List<ConceptModelNode>> = allConceptModelNodes
//            .groupBy { it.conceptName}
//            .mapValues { entry -> entry.value }
//            .toMap()
//
        //return ConceptModelGraphDefaultImpl(allConceptModelNodes, templateNodesByConcept)
        return ConceptModelGraphDefaultImpl(nodePool.allConceptModelNodes())
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

        inputDataEntry.facetValues.map { it.facetName }.forEach { facetName ->
            if (alreadyUsedFacetNames.contains(facetName)) {
                throw DuplicateFacetNameFoundModelException(inputDataEntry.conceptName, inputDataEntry.conceptIdentifier, facetName)
            }
            alreadyUsedFacetNames.add(facetName)
        }

    }



}
