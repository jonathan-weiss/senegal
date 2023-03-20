package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(override val facetName: FacetName)
    : MandatoryInputAndTemplateFacet<MandatoryConceptReferenceFacetKotlinType, MandatoryConceptFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<MandatoryConceptReferenceFacetKotlinType>
        get() = MandatoryConceptReferenceFacetType

    override val templateFacetType: MandatoryFacetType<MandatoryConceptFacetKotlinType>
        get() = MandatoryConceptFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> MandatoryConceptFacetKotlinType
        get() = { calculationData -> calculationData.inputFacetValues.facetValue(this)
            .let { calculationData.conceptModelNodePool.getConcept(it) }
        }

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
