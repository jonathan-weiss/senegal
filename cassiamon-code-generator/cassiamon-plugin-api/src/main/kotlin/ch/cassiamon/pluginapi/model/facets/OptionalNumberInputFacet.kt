package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberInputFacet private constructor(override val facetName: FacetName)
    : InputFacet<OptionalNumberFacetKotlinType> {

    override val inputFacetType: FacetType<OptionalNumberFacetKotlinType>
        get() = OptionalNumberFacetType

    companion object {
        fun of(facetName: String): OptionalNumberInputFacet {
            return OptionalNumberInputFacet(FacetName.of(facetName))
        }
    }
}
