package ch.senegal.engine.plugin.finder

import ch.senegal.plugin.Plugin

interface PluginFinder {

    fun findAllPlugins(): Set<Plugin>
}
