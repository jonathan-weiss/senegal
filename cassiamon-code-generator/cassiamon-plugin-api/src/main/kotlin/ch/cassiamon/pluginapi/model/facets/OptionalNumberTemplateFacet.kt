package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberTemplateFacet private constructor(override val facetName: FacetName)
    : TemplateFacet<OptionalNumberFacetKotlinType> {
    override val templateFacetType: FacetType<OptionalNumberFacetKotlinType>
        get() = OptionalNumberFacetType

    companion object {
        fun of(facetName: String): OptionalNumberTemplateFacet {
            return OptionalNumberTemplateFacet(FacetName.of(facetName))
        }
    }
}
