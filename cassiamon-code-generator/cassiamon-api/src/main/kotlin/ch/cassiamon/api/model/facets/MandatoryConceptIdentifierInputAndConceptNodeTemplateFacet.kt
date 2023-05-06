package ch.cassiamon.api.model.facets

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

class MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(override val facetName: FacetName)
    : MandatoryInputAndTemplateFacet<ConceptReferenceFacetKotlinType, ConceptFacetKotlinType> {

    override val inputFacetType: MandatoryFacetType<ConceptReferenceFacetKotlinType>
        get() = MandatoryConceptReferenceFacetType

    override val templateFacetType: MandatoryFacetType<ConceptFacetKotlinType>
        get() = MandatoryConceptFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> ConceptFacetKotlinType
        get() = { calculationData -> calculationData.inputFacetValues.facetValue(this)
            .let { calculationData.conceptModelNodePool.getConcept(it) }
        }

    fun facetValue(value: ConceptReferenceFacetKotlinType): InputFacetValue<ConceptReferenceFacetKotlinType> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
