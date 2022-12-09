package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept

sealed class ModelInstance {

    val childModelNodes: MutableList<ModelNode> = mutableListOf()

    abstract fun parentModelInstance(): ModelInstance?

    fun createAndAddModelNode(resolvedConcept: ResolvedConcept): ModelNode {
        val modelNode = ModelNode(
            resolvedConcept = resolvedConcept,
            parentModelInstance = this,
        )
        childModelNodes.add(modelNode)

        return modelNode
    }

}
