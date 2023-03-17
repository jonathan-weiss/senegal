package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTextInputAndTemplateFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<MandatoryTextFacetType> = MandatoryInputFacet(facetName),
    private val templateFacet: TemplateFacet<MandatoryTextFacetType> = MandatoryTemplateFacet(facetName),
    private val facetComposition: InputAndTemplateFacetCombo<MandatoryTextFacetType, MandatoryTextFacetType>
    = InputAndTemplateFacetCombo(facetName, inputFacet, templateFacet) { it.inputFacetValues.facetValue(inputFacet) }

): InputAndTemplateFacet<MandatoryTextFacetType, MandatoryTextFacetType> by facetComposition {

    companion object {
        fun of(facetName: String): MandatoryTextInputAndTemplateFacet {
            return MandatoryTextInputAndTemplateFacet(FacetName.of(facetName))
        }
    }
}
