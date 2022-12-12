package ch.senegal.plugin.finder

import ch.senegal.plugin.Plugin

/**
 * Use PluginProvider (instead of directly using Plugin) to support the kotlin object instances as Plugin.
 * Kotlin object classes cannot be instantiated.
 */
interface PluginProvider {

    fun fetchPlugins(): Set<Plugin>
}
