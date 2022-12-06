package ch.senegal.engine.model

import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.plugin.tree.PluginTreeCreator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ModelTreeTest {

    @Test
    fun createModelTree() {
        // arrange
        val pluginTree = createTestPluginTree()

        // act
        val modelTree = ModelTree(pluginTree)

        // assert
        assertNotNull(modelTree)

    }

    private fun createTestPluginTree(): PluginTree {
        return PluginTreeCreator.createPluginTree(emptySet())
    }
}
