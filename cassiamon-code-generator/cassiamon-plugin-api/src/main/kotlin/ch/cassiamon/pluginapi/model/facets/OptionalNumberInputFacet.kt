package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberInputFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<OptionalNumberFacetType> = OptionalInputFacet(facetName)
): InputFacet<OptionalNumberFacetType> by inputFacet {

    companion object {
        fun of(facetName: String): OptionalNumberInputFacet {
            return OptionalNumberInputFacet(FacetName.of(facetName))
        }
    }
}
