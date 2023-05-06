package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName

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
