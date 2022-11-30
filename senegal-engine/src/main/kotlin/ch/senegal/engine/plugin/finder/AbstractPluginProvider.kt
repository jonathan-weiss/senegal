package ch.senegal.engine.plugin.finder

import ch.senegal.engine.plugin.Plugin

abstract class AbstractPluginProvider(val plugins: Set<Plugin>): PluginProvider {
    override fun fetchPlugins(): Set<Plugin> {
        return plugins
    }
}
