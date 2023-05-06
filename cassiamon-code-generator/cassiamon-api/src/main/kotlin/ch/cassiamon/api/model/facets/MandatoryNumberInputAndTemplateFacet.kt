package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

class MandatoryNumberInputAndTemplateFacet private constructor(override val facetName: FacetName)
    : MandatoryInputAndTemplateFacet<NumberFacetKotlinType, NumberFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<NumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val templateFacetType: MandatoryFacetType<NumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    fun facetValue(value: NumberFacetKotlinType): InputFacetValue<NumberFacetKotlinType> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String): MandatoryNumberInputAndTemplateFacet {
            return MandatoryNumberInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
