package ch.senegal.pluginexample

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.finder.ServiceLoaderPluginFinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ServiceLoaderPluginFinderTest {

    @Test
    fun findAllPlugins() {
        // arrange
        val numberOfPlugins = ExamplePluginProvider().plugins.size
        val pluginFinder = ServiceLoaderPluginFinder

        // act
        val plugins = pluginFinder.findAllPlugins()

        // assert
        assertEquals(numberOfPlugins, plugins.size)
    }

}
