package ch.cassiamon.exampleapp.customizing.wrapper

import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.exampleapp.customizing.concepts.EntityConceptDescription

data class EntitiesConcept(private val model: ConceptModelNode) {
    fun entities(): List<EntityConcept> {
        return model
            .children(EntityConceptDescription.conceptName)
            .map { EntityConcept(it) }
    }
}
