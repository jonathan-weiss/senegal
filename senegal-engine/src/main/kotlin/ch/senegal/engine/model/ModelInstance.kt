package ch.senegal.engine.model

import ch.senegal.engine.plugin.tree.ConceptNode

sealed class ModelInstance {

    val childModelNodes: MutableList<ModelNode> = mutableListOf()

    abstract fun parentModelInstance(): ModelInstance?

    fun createAndAddModelNode(conceptNode: ConceptNode): ModelNode {
        val modelNode = ModelNode(
            conceptNode = conceptNode,
            parentModelInstance = this,
        )
        childModelNodes.add(modelNode)

        return modelNode
    }

}
