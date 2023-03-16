package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryNumberInputAndTemplateFacet private constructor(override val facetName: FacetName)
    : InputAndTemplateFacet<Long, Long> {

    companion object {
        fun of(facetName: String): MandatoryNumberInputAndTemplateFacet {
            return MandatoryNumberInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
