package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryNumberInputAndTemplateFacet private constructor(override val facetName: FacetName)
    : MandatoryInputAndTemplateFacet<NumberFacetKotlinType, NumberFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<NumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val templateFacetType: MandatoryFacetType<NumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    companion object {
        fun of(facetName: String): MandatoryNumberInputAndTemplateFacet {
            return MandatoryNumberInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
