package ch.cassiamon.api.model

import ch.cassiamon.api.ConceptName

interface ConceptModelGraph {

    fun allConceptModelNodes(): List<ConceptModelNode>
    fun conceptModelNodesByConceptName(conceptName: ConceptName): List<ConceptModelNode>
    fun conceptModelNodeByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptModelNode

}
