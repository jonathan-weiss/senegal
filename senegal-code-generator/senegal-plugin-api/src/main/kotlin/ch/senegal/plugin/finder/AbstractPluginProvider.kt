package ch.senegal.plugin.finder

import ch.senegal.plugin.Plugin

abstract class AbstractPluginProvider(val plugins: Set<Plugin>): PluginProvider {
    override fun fetchPlugins(): Set<Plugin> {
        return plugins
    }
}
