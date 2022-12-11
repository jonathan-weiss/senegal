package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.freemarker.templateengine.FreemarkerFileDescriptor
import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.plugin.TemplateTarget


object TemplateModelCreator {

    fun createTemplateTargets(modelTree: MutableModelTree, resolvedPlugins: ResolvedPlugins): List<FreemarkerFileDescriptor> {
        val templateModel = createTemplateModel(modelTree)
        val templateTargets = collectTemplateTargets(modelTree)
        val templateRootModel: Map<String, Any> = mapOf(
            "senegalTemplateModel" to templateModel
        )


        return templateTargets.map {
            FreemarkerFileDescriptor(it.targetFile, templateRootModel, it.templateClasspath)
        }
    }

    private fun collectTemplateTargets(modelTree: MutableModelTree): List<TemplateTarget> {
        return modelTree.getAllModelNodes()
            .flatMap { node -> node.resolvedConcept.enclosedPurposes
                .flatMap { purpose -> purpose.createTemplateTargets(node) } }
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
