package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.pluginexample.ExamplePluginProvider
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginFinderTest {

    @Test
    fun findAllPlugins() {
        // arrange
        val numberOfPlugins = ExamplePluginProvider().plugins.size

        // act
        val plugins = PluginFinder.findAllPlugins()

        // assert
        assertEquals(numberOfPlugins, plugins.size)
    }

}
