package ch.senegal.pluginexample

import ch.senegal.engine.plugin.finder.PluginFinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
