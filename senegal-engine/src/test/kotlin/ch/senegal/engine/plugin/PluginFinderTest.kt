package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.finder.PluginFinder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginFinderTest {

    @Test
    fun findAllPlugins() {
        // act
        val plugins = PluginFinder.findAllPlugins()

        // assert
        assertEquals(3, plugins.size)
    }

}
