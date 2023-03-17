package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryTextInputAndTemplateFacet private constructor(override val facetName: FacetName, )
    : InputAndTemplateFacet<MandatoryTextFacetKotlinType, MandatoryTextFacetKotlinType> {

    override val inputFacetType: FacetType<MandatoryTextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val templateFacetType: FacetType<MandatoryTextFacetKotlinType>
        get() = MandatoryTextFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> MandatoryTextFacetKotlinType
        get() = { it.inputFacetValues.facetValue(this) }

    companion object {
        fun of(facetName: String): MandatoryTextInputAndTemplateFacet {
            return MandatoryTextInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
