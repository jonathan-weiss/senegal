package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

class MandatoryTextTemplateFacet private constructor(
    override val facetName: FacetName,
    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType
    )
    : MandatoryTemplateFacet<TextFacetKotlinType> {

    override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    companion object {
        fun of(facetName: String, facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType): MandatoryTextTemplateFacet {
            return MandatoryTextTemplateFacet(FacetName.of(facetName), facetCalculationFunction)
        }
    }
}
