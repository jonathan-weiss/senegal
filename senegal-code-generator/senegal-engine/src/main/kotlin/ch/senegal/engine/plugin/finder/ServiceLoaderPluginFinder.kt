package ch.senegal.engine.plugin.finder

import ch.senegal.plugin.Plugin
import ch.senegal.plugin.finder.PluginProvider
import java.util.*

object ServiceLoaderPluginFinder: PluginFinder {

    override fun findAllPlugins(): Set<Plugin> {
        val pluginLoader: ServiceLoader<PluginProvider> = ServiceLoader.load(PluginProvider::class.java)
        return pluginLoader.flatMap { it.fetchPlugins() }.toSet()
    }
}
