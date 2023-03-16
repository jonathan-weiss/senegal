package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextTemplateFacet private constructor(override val facetName: FacetName): TemplateFacet<String> {
    companion object {
        fun of(facetName: String): MandatoryTextTemplateFacet {
            return MandatoryTextTemplateFacet(FacetName.of(facetName))
        }
    }
}
