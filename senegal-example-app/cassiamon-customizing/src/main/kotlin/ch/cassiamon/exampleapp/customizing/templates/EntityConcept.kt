package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.exampleapp.customizing.concepts.EntityAttributeConceptDescription
import ch.cassiamon.exampleapp.customizing.concepts.EntityConceptDescription

data class EntityConcept(private val model: ConceptModelNode) {
    val name = model.templateFacetValues.facetValue(EntityConceptDescription.nameFacet)

    fun entityAttributes(): List<EntityAttributeConcept> {
        return model
            .children(EntityAttributeConceptDescription.conceptName)
            .map { EntityAttributeConcept(it) }
    }
}
