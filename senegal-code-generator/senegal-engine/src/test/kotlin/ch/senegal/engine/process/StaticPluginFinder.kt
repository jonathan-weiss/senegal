package ch.senegal.engine.process

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.plugin.Plugin
import ch.senegal.plugin.finder.PluginProvider
import java.util.*

class StaticPluginFinder(private val plugins: Set<Plugin>): PluginFinder {

    override fun findAllPlugins(): Set<Plugin> {
        return plugins
    }
}
