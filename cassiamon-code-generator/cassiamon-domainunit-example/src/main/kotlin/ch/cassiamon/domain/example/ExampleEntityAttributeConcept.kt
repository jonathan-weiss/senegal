package ch.cassiamon.domain.example

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TextFacets

object ExampleEntityAttributeConcept {
    val conceptName = ConceptName.of("TestEntityAttribute")
    val nameFacet = TextFacets.ofMandatoryInputAndTemplate("TestEntityAttributeName")
}
