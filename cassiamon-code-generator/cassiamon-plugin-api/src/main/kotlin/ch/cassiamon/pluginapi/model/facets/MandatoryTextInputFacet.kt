package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextInputFacet private constructor(override val facetName: FacetName)
    : MandatoryInputFacet<MandatoryTextFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<MandatoryTextFacetKotlinType>
        get() = MandatoryTextFacetType

    companion object {
        fun of(facetName: String): MandatoryTextInputFacet {
            return MandatoryTextInputFacet(FacetName.of(facetName))
        }
    }
}
