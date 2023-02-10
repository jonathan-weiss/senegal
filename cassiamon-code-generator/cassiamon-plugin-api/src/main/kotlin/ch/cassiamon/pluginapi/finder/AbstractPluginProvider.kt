package ch.cassiamon.pluginapi.finder

import ch.cassiamon.pluginapi.Plugin

abstract class AbstractPluginProvider(val plugins: Set<ch.cassiamon.pluginapi.Plugin>):
    ch.cassiamon.pluginapi.finder.PluginProvider {
    override fun fetchPlugins(): Set<ch.cassiamon.pluginapi.Plugin> {
        return plugins
    }
}
