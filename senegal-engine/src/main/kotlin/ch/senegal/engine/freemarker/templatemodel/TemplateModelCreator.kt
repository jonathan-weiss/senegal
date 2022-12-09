package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.freemarker.templateengine.FreemarkerFileDescriptor
import ch.senegal.engine.model.ModelNode
import ch.senegal.engine.model.ModelTree
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.plugin.TemplateTarget


object TemplateModelCreator {

    fun createTemplateTargets(modelTree: ModelTree, resolvedPlugins: ResolvedPlugins): List<FreemarkerFileDescriptor> {
        val templateModel = createTemplateModel(modelTree)
        val templateTargets = collectTemplateTargets(modelTree)
        val templateRootModel: Map<String, Any> = mapOf(
            "senegalTemplateModel" to templateModel
        )


        return templateTargets.map {
            FreemarkerFileDescriptor(it.targetFile, templateRootModel, it.templateClasspath)
        }
    }

    private fun collectTemplateTargets(modelTree: ModelTree): List<TemplateTarget> {
        return modelTree.getAllModelNodes()
            .flatMap { node -> node.resolvedConcept.enclosedPurposes
                .flatMap { purpose -> purpose.createTemplateTargets() } }
    }

    private fun createTemplateModel(modelTree: ModelTree): List<TemplateModelNode> {
        return modelTree.getRootModelNodes().map { createTemplateModelNode(it) }
    }

    private fun createTemplateModelNode(modelNode: ModelNode): TemplateModelNode {
        val properties: Map<String, Any> = modelNode.modelDecorations
            .map { (key, value) -> Pair(key.purposeDecorName, value.value) }
            .toMap()
        return TemplateModelNode(
            properties = properties,
            childNodes = modelNode.childModelNodes.map { createTemplateModelNode(it) }
        )
    }


}
