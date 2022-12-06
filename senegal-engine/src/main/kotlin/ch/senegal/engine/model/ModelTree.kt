package ch.senegal.engine.model

import ch.senegal.engine.plugin.tree.PluginTree

class ModelTree(val pluginTree: PluginTree): ModelInstance() {

    override fun parentModelInstance(): ModelInstance? {
        return null
    }

    fun getRootModelNodes(): List<ModelNode> {
        return this.childModelNodes
    }

}
