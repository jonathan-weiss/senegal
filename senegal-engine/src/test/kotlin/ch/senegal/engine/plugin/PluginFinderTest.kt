package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.finder.PluginFinder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginFinderTest {

    @Test
    fun findAllConceptPlugins() {
        // act
        val conceptPlugins = PluginFinder.findAllConceptPlugins()

        // assert
        assertEquals(1, conceptPlugins.size)
    }

    @Test
    fun findAllRefinementPropertyPlugins() {
        // act
        val refinementPropertyPlugins = PluginFinder.findAllPurposePlugins()

        // assert
        assertEquals(1, refinementPropertyPlugins.size)
    }

}
