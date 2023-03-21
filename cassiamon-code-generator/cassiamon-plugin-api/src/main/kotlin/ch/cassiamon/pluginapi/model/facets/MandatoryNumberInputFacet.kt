package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryNumberInputFacet private constructor(override val facetName: FacetName): MandatoryInputFacet<NumberFacetKotlinType> {
    override val inputFacetType: MandatoryFacetType<NumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    fun facetValue(value: NumberFacetKotlinType): InputFacetValue<NumberFacetKotlinType> {
        return InputFacetValue(this, value)
    }


    companion object {
        fun of(facetName: String): MandatoryNumberInputFacet {
            return MandatoryNumberInputFacet(FacetName.of(facetName))
        }
    }
}
