package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextTemplateFacet private constructor(override val facetName: FacetName)
    : MandatoryTemplateFacet<TextFacetKotlinType> {

    override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    companion object {
        fun of(facetName: String): MandatoryTextTemplateFacet {
            return MandatoryTextTemplateFacet(FacetName.of(facetName))
        }
    }
}
