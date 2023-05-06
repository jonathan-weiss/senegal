package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName

class MandatoryTextInputFacet private constructor(override val facetName: FacetName)
    : MandatoryInputFacet<TextFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    fun facetValue(value: TextFacetKotlinType): InputFacetValue<TextFacetKotlinType> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String): MandatoryTextInputFacet {
            return MandatoryTextInputFacet(FacetName.of(facetName))
        }
    }
}
