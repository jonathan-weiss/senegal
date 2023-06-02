package ch.cassiamon.exampleapp.customizing.concepts

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TextFacets

object EntityConceptDescription {
    val conceptName = ConceptName.of("Entity")

    val nameFacet = TextFacets.ofMandatoryInputAndTemplate("EntityName")
    val numberFacet = TextFacets.ofMandatoryInputAndTemplate("TestEntityNumberName")
    val alternativeNameFacet = TextFacets.ofMandatoryTemplate("TestEntityAlternativeName") {
        return@ofMandatoryTemplate it.inputFacetValues.facetValue(nameFacet).uppercase()
    }
}
