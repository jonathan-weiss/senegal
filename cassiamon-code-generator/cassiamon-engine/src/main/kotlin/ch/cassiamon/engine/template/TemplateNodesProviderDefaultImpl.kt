package ch.cassiamon.engine.template

import ch.cassiamon.engine.model.graph.ModelConceptNode
import ch.cassiamon.engine.model.graph.ModelGraph
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateNode
import ch.cassiamon.pluginapi.template.TemplateNodeBag
import ch.cassiamon.pluginapi.template.TemplateNodesProvider
import ch.cassiamon.pluginapi.template.exceptions.TemplateException
import java.nio.file.Path

class TemplateNodesProviderDefaultImpl(modelGraph: ModelGraph) : TemplateNodesProvider {

    private val nodePool: Map<ConceptIdentifier, MutableTemplateNode>
    private val conceptIdentifiersByParentConceptIdentifier: Map<ConceptIdentifier, List<ConceptIdentifier>>

    private val emptyTemplateNodeBag = TemplateNodeBag(emptyList())
    private val allTemplateNodes: TemplateNodeBag
    private val templateNodesByConcept: Map<ConceptName, TemplateNodeBag>

    init {
        nodePool = createNodePool(modelGraph)
        conceptIdentifiersByParentConceptIdentifier = createConceptIdentifierByParent(modelGraph)
        linkTemplateNodes(modelGraph)

        allTemplateNodes = TemplateNodeBag(nodePool.values.toList())
        templateNodesByConcept = allTemplateNodes.nodes
            .groupBy { it.conceptName}
            .mapValues { entry -> TemplateNodeBag(entry.value) }
            .toMap()
    }

    override fun fetchAllTemplateNodes(): TemplateNodeBag {
        return allTemplateNodes
    }

    override fun fetchTemplateNodes(conceptName: ConceptName): TemplateNodeBag {
        return templateNodesByConcept[conceptName] ?: emptyTemplateNodeBag

    }

    override fun targetGeneratedFileForEachTemplateNode(
        conceptName: ConceptName,
        generatedFileMapper: (templateNode: TemplateNode) -> Path
    ): Set<TargetGeneratedFileWithModel> {
        return fetchTemplateNodes(conceptName).nodes
            .map {templateNode -> x(templateNode, generatedFileMapper) }
            .toSet()
    }

    private fun createNodePool(modelGraph: ModelGraph): Map<ConceptIdentifier, MutableTemplateNode> {
        val modelConceptNodes = modelGraph.resolvedEntries.values

        return modelConceptNodes.associateBy(
            { modelConceptNode -> modelConceptNode.conceptIdentifier},
            { modelConceptNode -> createUnlinkedTemplateNode(modelConceptNode)},
        ).toMap()
    }
    private fun createConceptIdentifierByParent(modelGraph: ModelGraph): Map<ConceptIdentifier, List<ConceptIdentifier>> {
        val modelConceptNodes = modelGraph.resolvedEntries.values

        return modelConceptNodes
            .filter { modelConceptNode -> modelConceptNode.parentConceptIdentifier != null }
            .groupBy(
                { modelConceptNode -> requireNotNull(modelConceptNode.parentConceptIdentifier)},
                { modelConceptNode -> modelConceptNode.conceptIdentifier}
            )
    }

    private fun linkTemplateNodes(modelGraph: ModelGraph) {
        val modelConceptNodes = modelGraph.resolvedEntries.values

        modelConceptNodes.forEach { modelConceptNode ->
            linkTemplateNode(templateModelFromNodePool(modelConceptNode.conceptIdentifier), modelConceptNode)
        }
    }

    private fun createUnlinkedTemplateNode(modelConceptNode: ModelConceptNode): MutableTemplateNode {
        return MutableTemplateNode(
            conceptName = modelConceptNode.conceptName,
            conceptIdentifier = modelConceptNode.conceptIdentifier,
            facetValues = MutableTemplateNodeFacetValues(modelConceptNode.facetValues),
            )
    }

    private fun linkTemplateNode(templateNode: MutableTemplateNode, modelConceptNode: ModelConceptNode) {
        val parentTemplateNode: MutableTemplateNode? = modelConceptNode.parentConceptIdentifier?.let { nodePool[it] }

        val childrenConceptIdentifiers = conceptIdentifiersByParentConceptIdentifier[modelConceptNode.conceptIdentifier] ?: emptyList()
        val childrenTemplateNodes = childrenConceptIdentifiers
                .map { templateModelFromNodePool(it) }
                .groupBy { it.conceptName }

        templateNode.assignHierarchicalTemplateNodes(parentTemplateNode, childrenTemplateNodes)

        val referenceTemplateNodeFacetValues = modelConceptNode.facetValues.facetValuesMap
            .filter { it.value is ConceptReferenceFacetValue }
            .map { Pair(it.key, it.value as ConceptReferenceFacetValue) }
            .associate { Pair(it.first, templateModelFromNodePool(it.second.conceptReference)) }
        templateNode.facetValues.assignTemplateNodeFacetValues(referenceTemplateNodeFacetValues)

    }

    private fun templateModelFromNodePool(conceptIdentifier: ConceptIdentifier): MutableTemplateNode {
        return nodePool[conceptIdentifier] ?: throw TemplateException("Concept with identifier '$conceptIdentifier' not found.")
    }

    private fun x(templateNode: TemplateNode, generatedFileMapper: (templateNode: TemplateNode) -> Path): TargetGeneratedFileWithModel {
        val file = generatedFileMapper(templateNode)
        return TargetGeneratedFileWithModel(file, templateNode.asTemplateNodeBag)
    }
}
