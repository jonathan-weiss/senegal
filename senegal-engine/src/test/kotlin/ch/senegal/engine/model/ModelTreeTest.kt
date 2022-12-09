package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.plugin.resolver.PluginResolver
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ModelTreeTest {

    @Test
    fun createModelTree() {
        // arrange
        val resolvedPlugins = resolvedPlugins()

        // act
        val modelTree = ModelTree(resolvedPlugins)

        // assert
        assertNotNull(modelTree)

    }

    private fun resolvedPlugins(): ResolvedPlugins {
        return PluginResolver.resolvePlugins(emptySet())
    }
}
