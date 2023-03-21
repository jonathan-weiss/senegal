package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberInputFacet private constructor(override val facetName: FacetName)
    : OptionalInputFacet<NumberFacetKotlinType?> {

    override val inputFacetType: OptionalFacetType<NumberFacetKotlinType?>
        get() = OptionalNumberFacetType

    fun facetValue(value: NumberFacetKotlinType?): InputFacetValue<NumberFacetKotlinType?> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String): OptionalNumberInputFacet {
            return OptionalNumberInputFacet(FacetName.of(facetName))
        }
    }
}
