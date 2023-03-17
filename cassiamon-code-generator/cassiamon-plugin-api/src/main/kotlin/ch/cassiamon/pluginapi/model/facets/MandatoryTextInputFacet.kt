package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextInputFacet private constructor(override val facetName: FacetName)
    : InputFacet<MandatoryTextFacetKotlinType> {

    override val inputFacetType: FacetType<MandatoryTextFacetKotlinType>
        get() = MandatoryTextFacetType

    companion object {
        fun of(facetName: String): MandatoryTextInputFacet {
            return MandatoryTextInputFacet(FacetName.of(facetName))
        }
    }
}
