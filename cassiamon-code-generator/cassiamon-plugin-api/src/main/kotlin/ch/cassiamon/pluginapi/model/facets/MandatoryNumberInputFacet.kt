package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryNumberInputFacet private constructor(facetName: FacetName,
                                                    private val inputFacet: InputFacet<MandatoryNumberFacetType> = MandatoryInputFacet(facetName)
): InputFacet<MandatoryNumberFacetType> by inputFacet {



    companion object {
        fun of(facetName: String): MandatoryNumberInputFacet {
            return MandatoryNumberInputFacet(FacetName.of(facetName))
        }
    }
}
