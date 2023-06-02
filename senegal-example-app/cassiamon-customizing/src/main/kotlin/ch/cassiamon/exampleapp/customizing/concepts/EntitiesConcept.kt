package ch.cassiamon.exampleapp.customizing.concepts

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TextFacets

object EntitiesConcept {
    val conceptName = ConceptName.of("Entities")

    val infoDescriptionFacet = TextFacets.ofMandatoryInputAndTemplate("InfoDescription")
}
