package ch.senegal.engine.plugin

import java.util.*

object PluginFinder {

    private fun findAllPlugins(): Set<Plugin> {
        val pluginLoader: ServiceLoader<Plugin> = ServiceLoader.load(Plugin::class.java)
        return pluginLoader.toSet()
    }

    fun findAllConceptPlugins(): Set<Concept> {
        return this.findAllPlugins().filterIsInstance<Concept>().toSet()
    }

    fun findAllPurposePlugins(): Set<Purpose> {
        return this.findAllPlugins().filterIsInstance<Purpose>().toSet()
    }

}
