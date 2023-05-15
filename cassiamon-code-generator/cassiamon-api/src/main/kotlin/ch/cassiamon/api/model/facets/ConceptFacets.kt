package ch.cassiamon.api.model.facets

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

object ConceptFacets {

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
            fun of(facetName: FacetName, referencedConceptName: ConceptName): MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet {
                // TODO what is with the target concept?
                return MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet(facetName)
            }
        }
    }

    class OptionalConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(override val facetName: FacetName)
        : OptionalInputAndTemplateFacet<ConceptReferenceFacetKotlinType?, ConceptFacetKotlinType?> {

        override val inputFacetType: OptionalFacetType<ConceptReferenceFacetKotlinType?>
            get() = OptionalConceptReferenceFacetType

        override val templateFacetType: OptionalFacetType<ConceptFacetKotlinType?>
            get() = OptionalConceptFacetType

        override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> ConceptFacetKotlinType?
            get() = { calculationData -> calculationData.inputFacetValues.facetValue(this)
                ?.let { calculationData.conceptModelNodePool.getConcept(it) } }

        fun facetValue(value: ConceptReferenceFacetKotlinType?): InputFacetValue<ConceptReferenceFacetKotlinType?> {
            return InputFacetValue(this, value)
        }

        companion object {
            fun of(facetName: FacetName, referencedConceptName: ConceptName): OptionalConceptIdentifierInputAndConceptNodeTemplateFacet {
                // TODO what is with the target concept?
                return OptionalConceptIdentifierInputAndConceptNodeTemplateFacet(facetName)
            }
        }
    }

    fun ofMandatoryConceptIdentifier(facetName: String, referencedConceptName: ConceptName): MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet {
        return MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet.of(FacetName.of(facetName), referencedConceptName)
    }

    fun ofOptionalConceptIdentifier(facetName: String, referencedConceptName: ConceptName): OptionalConceptIdentifierInputAndConceptNodeTemplateFacet {
        return OptionalConceptIdentifierInputAndConceptNodeTemplateFacet.of(FacetName.of(facetName), referencedConceptName)
    }



}
