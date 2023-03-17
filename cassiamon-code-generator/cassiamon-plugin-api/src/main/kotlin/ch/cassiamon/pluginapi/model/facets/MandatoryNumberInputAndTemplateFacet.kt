package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryNumberInputAndTemplateFacet private constructor(override val facetName: FacetName)
    : InputAndTemplateFacet<MandatoryNumberFacetKotlinType, MandatoryNumberFacetKotlinType> {

    override val inputFacetType: FacetType<MandatoryNumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val templateFacetType: FacetType<MandatoryNumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> MandatoryNumberFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    companion object {
        fun of(facetName: String): MandatoryNumberInputAndTemplateFacet {
            return MandatoryNumberInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
