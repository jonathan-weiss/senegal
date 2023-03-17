package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextInputFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<MandatoryTextFacetType> = MandatoryInputFacet(facetName)
): InputFacet<MandatoryTextFacetType> by inputFacet {

    companion object {
        fun of(facetName: String): MandatoryTextInputFacet {
            return MandatoryTextInputFacet(FacetName.of(facetName))
        }
    }
}
