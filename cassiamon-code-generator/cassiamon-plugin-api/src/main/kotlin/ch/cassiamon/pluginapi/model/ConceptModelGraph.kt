package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.ConceptName

interface ConceptModelGraph {

    fun allConceptModelNodes(): List<ConceptModelNode>
    fun conceptModelNodesByConceptName(conceptName: ConceptName): List<ConceptModelNode>
    fun conceptModelNodeByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptModelNode

}
