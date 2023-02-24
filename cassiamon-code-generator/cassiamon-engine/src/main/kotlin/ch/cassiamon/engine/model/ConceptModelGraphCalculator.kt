package ch.cassiamon.engine.model

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.schema.types.Concept
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.model.ConceptModelNode

object ConceptModelGraphCalculator {

    fun calculateConceptModelGraph(schema: Schema, modelInputData: ModelInputData): ConceptModelGraph {
        val nodePool: Map<ConceptIdentifier, MutableConceptModelNode> = createNodePool(schema, modelInputData)
        val conceptIdentifiersByParentConceptIdentifier: Map<ConceptIdentifier, List<ConceptIdentifier>> = createConceptIdentifierByParent(modelInputData)


        // linkTemplateNodes(calculatedModel)

        val allConceptModelNodes: List<ConceptModelNode> = nodePool.values.toList()
        val templateNodesByConcept: Map<ConceptName, List<ConceptModelNode>> = allConceptModelNodes
            .groupBy { it.conceptName}
            .mapValues { entry -> entry.value }
            .toMap()

        return ConceptModelGraphDefaultImpl(allConceptModelNodes, templateNodesByConcept)
    }



    private fun createNodePool(schema: Schema,modelInputData: ModelInputData): Map<ConceptIdentifier, MutableConceptModelNode> {
        val modelConceptNodes = modelInputData.entries.map { createConceptModelNodeFromInputEntry(schema, it) }

        return modelConceptNodes.associateBy(
            { modelConceptNode -> modelConceptNode.conceptIdentifier},
            { modelConceptNode -> modelConceptNode},
        ).toMap()
    }

    private fun createConceptModelNodeFromInputEntry(
        schema: Schema,
        entry: ModelConceptInputDataEntry,
    ): MutableConceptModelNode {

        val schemaConcept: Concept = schema.conceptByConceptName(entry.conceptName)

        val facetValues = entry.facetValuesMap.toMutableMap()

        return MutableConceptModelNode(
            conceptName = entry.conceptName,
            conceptIdentifier = entry.conceptIdentifier,
            facetValues = MutableConceptModelNodeFacetValues(facetValues)
        )
    }

    private fun createConceptIdentifierByParent(modelInputData: ModelInputData): Map<ConceptIdentifier, List<ConceptIdentifier>> {
        val modelConceptNodes = modelInputData.entries

        return modelConceptNodes
            .filter { modelConceptNode -> modelConceptNode.parentConceptIdentifier != null }
            .groupBy(
                { modelConceptNode -> requireNotNull(modelConceptNode.parentConceptIdentifier)},
                { modelConceptNode -> modelConceptNode.conceptIdentifier}
            )
    }

//    private fun linkTemplateNodes(calculatedModel: CalculatedModel) {
//        val modelConceptNodes = calculatedModel.calculatedEntries.values
//
//        modelConceptNodes.forEach { modelConceptNode ->
//            linkTemplateNode(templateModelFromNodePool(modelConceptNode.conceptIdentifier), modelConceptNode)
//        }
//    }
//
//    private fun linkTemplateNode(templateNode: MutableConceptModelNode, calculatedModelConceptNode: CalculatedModelConceptNode) {
//        val parentTemplateNode: MutableConceptModelNode? = calculatedModelConceptNode.parentConceptIdentifier?.let { nodePool[it] }
//
//        val childrenConceptIdentifiers = conceptIdentifiersByParentConceptIdentifier[calculatedModelConceptNode.conceptIdentifier] ?: emptyList()
//        val childrenTemplateNodes = childrenConceptIdentifiers
//            .map { templateModelFromNodePool(it) }
//            .groupBy { it.conceptName }
//
//        templateNode.assignHierarchicalTemplateNodes(parentTemplateNode, childrenTemplateNodes)
//
//        val referenceTemplateNodeFacetValues = calculatedModelConceptNode.facetValues.facetValuesMap
//            .filter { it.value is ConceptReferenceFacetValue }
//            .map { Pair(it.key, it.value as ConceptReferenceFacetValue) }
//            .associate { Pair(it.first, templateModelFromNodePool(it.second.conceptReference)) }
//        templateNode.facetValues.assignTemplateNodeFacetValues(referenceTemplateNodeFacetValues)
//
//    }
//
//    private fun templateModelFromNodePool(conceptIdentifier: ConceptIdentifier): MutableConceptModelNode {
//        return nodePool[conceptIdentifier] ?: throw ReferenceConceptModelNodeNotFoundException("Concept with identifier '$conceptIdentifier' not found.")
//    }

}
