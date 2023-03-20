package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryTextInputAndTemplateFacet private constructor(override val facetName: FacetName, )
    : MandatoryInputAndTemplateFacet<TextFacetKotlinType, TextFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    companion object {
        fun of(facetName: String): MandatoryTextInputAndTemplateFacet {
            return MandatoryTextInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
