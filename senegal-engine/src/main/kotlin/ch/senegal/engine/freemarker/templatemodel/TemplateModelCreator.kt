package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.model.ModelNode
import ch.senegal.engine.model.ModelTree

object TemplateModelCreator {

    fun createTemplateModel(modelTree: ModelTree): List<TemplateModelNode> {
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
