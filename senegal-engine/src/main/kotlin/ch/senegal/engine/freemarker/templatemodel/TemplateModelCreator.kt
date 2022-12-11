package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.freemarker.templateengine.FreemarkerFileDescriptor
import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.plugin.Purpose
import java.nio.file.Path


object TemplateModelCreator {
    val rootModelListName = "rootTemplateModels"
    val currentModelName = "templateModel"


    fun createTemplateTargets(modelTree: MutableModelTree, resolvedPlugins: ResolvedPlugins, defaultOutputPath: Path): List<FreemarkerFileDescriptor> {
        val modelNodeToTemplateModelMap = createTemplateModelNodeMap(modelTree)
        val rootTemplateModelList = createTemplateModel(modelTree, modelNodeToTemplateModelMap)
        return collectTemplateTargets(
            modelTree = modelTree,
            defaultOutputPath = defaultOutputPath,
            modelNodeToTemplateModelMap = modelNodeToTemplateModelMap,
            rootTemplateModelList = rootTemplateModelList)
    }

    private fun collectTemplateTargets(modelTree: MutableModelTree,
                                       defaultOutputPath: Path,
                                       modelNodeToTemplateModelMap: Map<MutableModelNode, TemplateModelNode>,
                                       rootTemplateModelList: List<TemplateModelNode>): List<FreemarkerFileDescriptor> {
        return modelTree.getAllModelNodes()
            .flatMap { node -> node.resolvedConcept.enclosedPurposes
                .flatMap { purpose -> createFreemarkerFileDescriptors(
                    mutableModelNode = node,
                    purpose = purpose,
                    defaultOutputPath = defaultOutputPath,
                    modelNodeToTemplateModelMap = modelNodeToTemplateModelMap,
                    rootTemplateModelList = rootTemplateModelList) } }
    }

    private fun createFreemarkerFileDescriptors(mutableModelNode: MutableModelNode,
                                                purpose: Purpose,
                                                defaultOutputPath: Path,
                                                modelNodeToTemplateModelMap: Map<MutableModelNode, TemplateModelNode>,
                                                rootTemplateModelList: List<TemplateModelNode>
    ): List<FreemarkerFileDescriptor> {
        val templateTargets = purpose.createTemplateTargets(mutableModelNode, defaultOutputPath).toList()

        val templateRootModel: Map<String, Any> = mapOf(
            rootModelListName to rootTemplateModelList,
            currentModelName to requireNotNull(modelNodeToTemplateModelMap[mutableModelNode])
        )

        return templateTargets.map {
            FreemarkerFileDescriptor(it.targetFile, templateRootModel, it.templateClasspath)
        }

    }

    private fun createTemplateModelNodeMap(modelTree: MutableModelTree): Map<MutableModelNode, TemplateModelNode> {
        return modelTree.getAllModelNodes().associateWith { createTemplateModelNode(it) }
    }

    internal fun createTemplateModel(modelTree: MutableModelTree,
                                     modelNodeToTemplateModelMap: Map<MutableModelNode, TemplateModelNode>): List<TemplateModelNode> {
        return modelTree.getRootModelNodes().map { requireNotNull(modelNodeToTemplateModelMap[it]) }
    }


    internal fun createTemplateModel(modelTree: MutableModelTree): List<TemplateModelNode> {
        return modelTree.getRootModelNodes().map { createTemplateModelNode(it) }
    }

    private fun createTemplateModelNode(mutableModelNode: MutableModelNode): TemplateModelNode {
        val properties: Map<String, Any> = mutableModelNode.nodeFacetValues
            .map { (purposeFacetCombinedName, facetValue) -> Pair(purposeFacetCombinedName.name, facetValue.value) }
            .toMap()
        return TemplateModelNode(
            properties = properties,
            childNodes = mutableModelNode.mutableChildModelNodes.map { createTemplateModelNode(it) }
        )
    }


}
