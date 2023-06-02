package ch.cassiamon.exampleapp.customizing.concepts

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TextFacets

object EntityAttributeConcept {
    val conceptName = ConceptName.of("EntityAttribute")
    val nameFacet = TextFacets.ofMandatoryInputAndTemplate("EntityAttributeName")
    val typeFacet = TextFacets.ofMandatoryInputAndTemplate("EntityAttributeType")
}
