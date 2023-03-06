package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

class MutableConceptModelNodePool: ConceptModelNodePool {
    private val nodePool: MutableMap<ConceptIdentifier, MaterializingConceptModelNode> = mutableMapOf()


    override fun containsConcept(conceptIdentifier: ConceptIdentifier): Boolean {
        return nodePool.containsKey(conceptIdentifier)
    }

    fun addConceptModelNode(conceptModelNode: MaterializingConceptModelNode) {
        require(!nodePool.containsKey(conceptModelNode.conceptIdentifier)) {
            "Node with conceptIdentifier '${conceptModelNode.conceptIdentifier}' already exists."
        }
        nodePool[conceptModelNode.conceptIdentifier] = conceptModelNode
    }

    override fun allConceptModelNodes(): List<ConceptModelNode> {
        return nodePool.values.toList()
    }
}
