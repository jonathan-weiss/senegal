package ch.cassiamon.domain.example

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TextFacets

object ExampleEntityConcept {
    val conceptName = ConceptName.of("TestEntity")

    val nameFacet = TextFacets.ofMandatoryInputAndTemplate("TestEntityName")
    val numberFacet = TextFacets.ofMandatoryInputAndTemplate("TestEntityNumberName")
    val alternativeNameFacet = TextFacets.ofMandatoryTemplate("TestEntityAlternativeName") {
        return@ofMandatoryTemplate it.inputFacetValues.facetValue(nameFacet).uppercase()
    }
}
