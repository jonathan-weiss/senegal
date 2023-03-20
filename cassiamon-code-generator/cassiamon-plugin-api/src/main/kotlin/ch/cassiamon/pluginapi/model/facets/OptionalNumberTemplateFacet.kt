package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberTemplateFacet private constructor(override val facetName: FacetName)
    : OptionalTemplateFacet<NumberFacetKotlinType?> {
    override val templateFacetType: OptionalFacetType<NumberFacetKotlinType?>
        get() = OptionalNumberFacetType

    companion object {
        fun of(facetName: String): OptionalNumberTemplateFacet {
            return OptionalNumberTemplateFacet(FacetName.of(facetName))
        }
    }
}
