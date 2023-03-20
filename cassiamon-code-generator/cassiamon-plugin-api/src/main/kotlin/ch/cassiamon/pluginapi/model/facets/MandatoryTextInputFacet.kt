package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextInputFacet private constructor(override val facetName: FacetName)
    : MandatoryInputFacet<TextFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
        get() = MandatoryTextFacetType

    companion object {
        fun of(facetName: String): MandatoryTextInputFacet {
            return MandatoryTextInputFacet(FacetName.of(facetName))
        }
    }
}
