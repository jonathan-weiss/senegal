package ch.cassiamon.exampleapp.customizing.wrapper

import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.exampleapp.customizing.concepts.EntityAttributeConceptDescription

data class EntityAttributeConcept(private val model: ConceptModelNode) {
    val name = model.templateFacetValues.facetValue(EntityAttributeConceptDescription.nameFacet)
    val type = model.templateFacetValues.facetValue(EntityAttributeConceptDescription.typeFacet)
}
