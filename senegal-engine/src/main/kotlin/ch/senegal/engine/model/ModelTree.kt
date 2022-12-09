package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedPlugins

class ModelTree(val resolvedPlugins: ResolvedPlugins): ModelInstance() {

    override fun parentModelInstance(): ModelInstance? {
        return null
    }

    fun getRootModelNodes(): List<ModelNode> {
        return this.childModelNodes
    }

}
