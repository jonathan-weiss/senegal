package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<MandatoryConceptReferenceFacetType> = MandatoryInputFacet(facetName),
    private val templateFacet: TemplateFacet<MandatoryConceptFacetType> = MandatoryTemplateFacet(facetName),
    private val facetComposition: InputAndTemplateFacetCombo<MandatoryConceptReferenceFacetType, MandatoryConceptFacetType>
    = InputAndTemplateFacetCombo(facetName, inputFacet, templateFacet)
    { calculationData -> calculationData.inputFacetValues.facetValue(inputFacet)
        .let { calculationData.conceptModelNodePool.getConcept(it) } }
): InputAndTemplateFacet<MandatoryConceptReferenceFacetType, MandatoryConceptFacetType> by facetComposition {

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
