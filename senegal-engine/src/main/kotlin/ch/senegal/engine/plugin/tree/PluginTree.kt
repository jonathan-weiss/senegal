package ch.senegal.engine.plugin.tree

import ch.senegal.plugin.ConceptName

/**
 * A hierarchical view to all plugins (= concept and purpose).
 */
class PluginTree(
    val rootConceptNodes: Set<ConceptNode>,

) {
    val allConceptNodes: Map<ConceptName, ConceptNode> =
        deepConceptNodes(rootConceptNodes).associateBy { it.concept.conceptName }

    fun getConceptNodeByName(name: ConceptName): ConceptNode? {
        return allConceptNodes[name]
    }

    companion object {
        private fun deepConceptNodes(conceptNodes: Set<ConceptNode>): Set<ConceptNode> {
            return conceptNodes.flatMap { childConceptNodesAtItself(it) }.toSet()
        }

        private fun childConceptNodesAtItself(conceptNode: ConceptNode): Set<ConceptNode> {
            return conceptNode.enclosedConcepts.flatMap { childConceptNodesAtItself(it) }.toSet() + conceptNode
        }
    }

}
