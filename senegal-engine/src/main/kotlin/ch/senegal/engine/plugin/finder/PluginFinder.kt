package ch.senegal.engine.plugin.finder

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.Plugin
import ch.senegal.engine.plugin.Purpose
import java.util.*

object PluginFinder {

    private fun findAllPlugins(): Set<Plugin> {
        val pluginLoader: ServiceLoader<Plugin> = ServiceLoader.load(Plugin::class.java)
        return pluginLoader.toSet()
    }

    fun findAllConceptPlugins(): Set<Concept> {
        return findAllPlugins().filterIsInstance<Concept>().toSet()
    }

    fun findAllPurposePlugins(): Set<Purpose> {
        return findAllPlugins().filterIsInstance<Purpose>().toSet()
    }

}
