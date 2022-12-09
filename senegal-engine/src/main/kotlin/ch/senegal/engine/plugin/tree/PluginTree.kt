package ch.senegal.engine.plugin.tree

import ch.senegal.plugin.ConceptName

/**
 * A hierarchical view to all plugins (= concept and purpose).
 */
class PluginTree(
    val allConceptNodes: Set<ConceptNode>,

    ) {
    val rootNotallConceptNodes: Map<ConceptName, ConceptNode> =
        deepConceptNodes(allConceptNodes).associateBy { it.concept.conceptName }

    val rootConceptNodes: List<ConceptNode>
        get() = allConceptNodes.filter { it.concept.enclosingConceptName == null }

    fun getConceptNodeByName(name: ConceptName): ConceptNode? {
        return allConceptNodes.associateBy { it.concept.conceptName }[name]
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
