package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

interface ConceptModelNodePool {

    fun allConceptModelNodes(): List<ConceptModelNode>
    fun containsConcept(conceptIdentifier: ConceptIdentifier): Boolean
}
