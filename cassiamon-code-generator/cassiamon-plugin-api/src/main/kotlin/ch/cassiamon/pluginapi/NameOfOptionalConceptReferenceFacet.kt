package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptIdentifier


class NameOfOptionalConceptReferenceFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfOptionalConceptReferenceFacet {
            return NameOfOptionalConceptReferenceFacet(name)
        }
    }
}
