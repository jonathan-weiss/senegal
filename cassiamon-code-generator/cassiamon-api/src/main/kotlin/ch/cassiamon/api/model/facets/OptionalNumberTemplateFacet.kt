package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

class OptionalNumberTemplateFacet private constructor(
    override val facetName: FacetName,
    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType?
    )
    : OptionalTemplateFacet<NumberFacetKotlinType?> {
    override val templateFacetType: OptionalFacetType<NumberFacetKotlinType?>
        get() = OptionalNumberFacetType

    companion object {
        fun of(facetName: String, facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType?): OptionalNumberTemplateFacet {
            return OptionalNumberTemplateFacet(FacetName.of(facetName), facetCalculationFunction)
        }
    }
}
