package ch.cassiamon.engine.model

import ch.cassiamon.api.model.*
import ch.cassiamon.api.model.exceptions.ConceptNotFoundModelException

class MutableConceptModelNodePool: ConceptModelNodePool {
    private val nodePool: MutableMap<ConceptIdentifier, ConceptModelNode> = mutableMapOf()


    override fun containsConcept(conceptIdentifier: ConceptIdentifier): Boolean {
        return nodePool.containsKey(conceptIdentifier)
    }

    override fun allConceptModelNodes(): List<ConceptModelNode> {
        return nodePool.values.toList()
    }

    override fun getConcept(conceptIdentifier: ConceptIdentifier): ConceptModelNode {
        return nodePool[conceptIdentifier]
            ?: throw ConceptNotFoundModelException(conceptIdentifier)
    }

    fun addConceptModelNode(conceptModelNode: ConceptModelNode) {
        require(!nodePool.containsKey(conceptModelNode.conceptIdentifier)) {
            "Node with conceptIdentifier '${conceptModelNode.conceptIdentifier}' already exists."
        }
        nodePool[conceptModelNode.conceptIdentifier] = conceptModelNode
    }

}
