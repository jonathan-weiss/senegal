package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextTemplateFacet private constructor(
    override val facetName: FacetName,
    private val templateFacet: TemplateFacet<MandatoryTextFacetType> = MandatoryTemplateFacet(facetName)
): TemplateFacet<MandatoryTextFacetType> by templateFacet {

    companion object {
        fun of(facetName: String): MandatoryTextTemplateFacet {
            return MandatoryTextTemplateFacet(FacetName.of(facetName))
        }
    }
}
