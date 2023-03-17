package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.model.InputFacetValueAccess
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodePool
import ch.cassiamon.pluginapi.model.exceptions.ConceptNotFoundModelException

class MutableConceptModelNodePool: ConceptModelNodePool {
    private val nodePool: MutableMap<ConceptIdentifier, ConceptModelNodeCalculationDataImpl> = mutableMapOf()


    override fun containsConcept(conceptIdentifier: ConceptIdentifier): Boolean {
        return nodePool.containsKey(conceptIdentifier)
    }

    override fun allConceptModelNodes(): List<ConceptModelNode> {
        return nodePool.values.map { it.conceptModelNode }.toList()
    }

    override fun getConcept(conceptIdentifier: ConceptIdentifier): ConceptModelNode {
        return nodePool[conceptIdentifier]?.conceptModelNode
            ?: throw ConceptNotFoundModelException(conceptIdentifier)
    }

    fun addConceptModelNode(conceptModelNode: ConceptModelNode, inputFacetValues: InputFacetValueAccess) {
        require(!nodePool.containsKey(conceptModelNode.conceptIdentifier)) {
            "Node with conceptIdentifier '${conceptModelNode.conceptIdentifier}' already exists."
        }
        nodePool[conceptModelNode.conceptIdentifier] = ConceptModelNodeCalculationDataImpl(
            conceptModelNode = conceptModelNode,
            inputFacetValues = inputFacetValues,
            conceptModelNodePool = this
        )
    }

}
