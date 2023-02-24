package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptIdentifier


class NameOfMandatoryConceptReferenceFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfMandatoryConceptReferenceFacet {
            return NameOfMandatoryConceptReferenceFacet(name)
        }
    }
}
