package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.model.ConceptModelNode

class ConceptModelGraphDefaultImpl(private val allConceptModelNodesList: List<ConceptModelNode>) : ConceptModelGraph {

    private val conceptModelNodesByConceptNameMap: Map<ConceptName, List<ConceptModelNode>> = allConceptModelNodesList.groupBy { it.conceptName }

    override fun allConceptModelNodes(): List<ConceptModelNode> {
        return allConceptModelNodesList
    }

    override fun conceptModelNodesByConceptName(conceptName: ConceptName): List<ConceptModelNode> {
        return conceptModelNodesByConceptNameMap[conceptName] ?: emptyList()
    }

    override fun conceptModelNodeByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptModelNode {
        return allConceptModelNodesList.first { it.conceptIdentifier == conceptIdentifier }
    }
}
