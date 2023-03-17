package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryNumberInputFacet private constructor(override val facetName: FacetName): InputFacet<MandatoryNumberFacetKotlinType> {
    override val inputFacetType: FacetType<MandatoryNumberFacetKotlinType>
        get() = MandatoryNumberFacetType

    companion object {
        fun of(facetName: String): MandatoryNumberInputFacet {
            return MandatoryNumberInputFacet(FacetName.of(facetName))
        }
    }
}
