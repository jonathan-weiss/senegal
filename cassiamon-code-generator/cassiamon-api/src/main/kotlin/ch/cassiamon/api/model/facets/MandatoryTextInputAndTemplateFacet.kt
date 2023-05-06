package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

class MandatoryTextInputAndTemplateFacet private constructor(override val facetName: FacetName, )
    : MandatoryInputAndTemplateFacet<TextFacetKotlinType, TextFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    fun facetValue(value: TextFacetKotlinType): InputFacetValue<TextFacetKotlinType> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String): MandatoryTextInputAndTemplateFacet {
            return MandatoryTextInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
