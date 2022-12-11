package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept

sealed class MutableModelInstance {

    val mutableChildModelNodes: MutableList<MutableModelNode> = mutableListOf()

    abstract fun parentMutableModelInstance(): MutableModelInstance?

    fun createAndAddMutableModelNode(resolvedConcept: ResolvedConcept): MutableModelNode {
        val mutableModelNode = MutableModelNode(
            resolvedConcept = resolvedConcept,
            parentMutableModelInstance = this,
        )
        mutableChildModelNodes.add(mutableModelNode)

        return mutableModelNode
    }

}
