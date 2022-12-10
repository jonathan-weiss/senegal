package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.plugin.model.ModelNode

sealed class MutableModelInstance: ModelNode {

    val mutableChildModelNodes: MutableList<MutableModelNode> = mutableListOf()

    abstract fun parentModelInstance(): MutableModelInstance?

    fun createAndAddMutableModelNode(resolvedConcept: ResolvedConcept): MutableModelNode {
        val mutableModelNode = MutableModelNode(
            resolvedConcept = resolvedConcept,
            parentMutableModelInstance = this,
        )
        mutableChildModelNodes.add(mutableModelNode)

        return mutableModelNode
    }

}
