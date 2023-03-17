package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryNumberInputAndTemplateFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<MandatoryNumberFacetType> = MandatoryInputFacet(facetName),
    private val templateFacet: TemplateFacet<MandatoryNumberFacetType> = MandatoryTemplateFacet(facetName),
    private val facetComposition: InputAndTemplateFacetCombo<MandatoryNumberFacetType, MandatoryNumberFacetType>
    = InputAndTemplateFacetCombo(facetName, inputFacet, templateFacet) { it.inputFacetValues.facetValue(inputFacet) }
): InputAndTemplateFacet<MandatoryNumberFacetType, MandatoryNumberFacetType> by facetComposition {

    companion object {
        fun of(facetName: String): MandatoryNumberInputAndTemplateFacet {
            return MandatoryNumberInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
