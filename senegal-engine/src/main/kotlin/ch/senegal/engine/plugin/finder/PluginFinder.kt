package ch.senegal.engine.plugin.finder

import ch.senegal.engine.plugin.Plugin
import java.util.*

object PluginFinder {

    fun findAllPlugins(): Set<Plugin> {
        val pluginLoader: ServiceLoader<PluginProvider> = ServiceLoader.load(PluginProvider::class.java)
        return pluginLoader.flatMap { it.fetchPlugins() }.toSet()
    }
}
