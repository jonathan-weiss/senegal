package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedPlugins

class MutableModelTree(val resolvedPlugins: ResolvedPlugins): MutableModelInstance() {

    override fun parentMutableModelInstance(): MutableModelInstance? {
        return null
    }

    fun getRootModelNodes(): List<MutableModelNode> {
        return this.mutableChildModelNodes
    }

    fun getAllModelNodes(): List<MutableModelNode> {
        return this.mutableChildModelNodes.flatMap { selfAndChildNodes(it) }
    }

    private fun selfAndChildNodes(mutableModelNode: MutableModelNode): List<MutableModelNode> {
        return listOf(mutableModelNode) + mutableModelNode.mutableChildModelNodes.flatMap { selfAndChildNodes(it) }
    }

}
