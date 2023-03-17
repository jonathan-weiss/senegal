package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalNumberTemplateFacet private constructor(
    override val facetName: FacetName,
    private val templateFacet: TemplateFacet<OptionalNumberFacetType> = OptionalTemplateFacet(facetName)
    ): TemplateFacet<OptionalNumberFacetType> by templateFacet {

    companion object {
        fun of(facetName: String): OptionalNumberTemplateFacet {
            return OptionalNumberTemplateFacet(FacetName.of(facetName))
        }
    }
}
