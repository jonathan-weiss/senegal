package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

object NumberFacets {

    class MandatoryNumberInputFacet private constructor(override val facetName: FacetName): MandatoryInputFacet<NumberFacetKotlinType> {
        override val inputFacetType: MandatoryFacetType<NumberFacetKotlinType>
            get() = MandatoryNumberFacetType

        fun facetValue(value: NumberFacetKotlinType): InputFacetValue<NumberFacetKotlinType> {
            return InputFacetValue(this, value)
        }


        companion object {
            fun of(facetName: FacetName): MandatoryNumberInputFacet {
                return MandatoryNumberInputFacet(facetName)
            }
        }
    }

    class OptionalNumberInputFacet private constructor(override val facetName: FacetName)
        : OptionalInputFacet<NumberFacetKotlinType?> {

        override val inputFacetType: OptionalFacetType<NumberFacetKotlinType?>
            get() = OptionalNumberFacetType

        fun facetValue(value: NumberFacetKotlinType?): InputFacetValue<NumberFacetKotlinType?> {
            return InputFacetValue(this, value)
        }

        companion object {
            fun of(facetName: FacetName): OptionalNumberInputFacet {
                return OptionalNumberInputFacet(facetName)
            }
        }
    }

    class MandatoryNumberInputAndTemplateFacet private constructor(override val facetName: FacetName)
        : MandatoryInputAndTemplateFacet<NumberFacetKotlinType, NumberFacetKotlinType> {

        override val inputFacetType: MandatoryFacetType<NumberFacetKotlinType>
            get() = MandatoryNumberFacetType

        override val templateFacetType: MandatoryFacetType<NumberFacetKotlinType>
            get() = MandatoryNumberFacetType

        override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType
            get() = { it.inputFacetValues.facetValue(this) }

        fun facetValue(value: NumberFacetKotlinType): InputFacetValue<NumberFacetKotlinType> {
            return InputFacetValue(this, value)
        }

        companion object {
            fun of(facetName: FacetName): MandatoryNumberInputAndTemplateFacet {
                return MandatoryNumberInputAndTemplateFacet(facetName)
            }
        }
    }


    class OptionalNumberTemplateFacet private constructor(
        override val facetName: FacetName,
        override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType?
    )
        : OptionalTemplateFacet<NumberFacetKotlinType?> {
        override val templateFacetType: OptionalFacetType<NumberFacetKotlinType?>
            get() = OptionalNumberFacetType

        companion object {
            fun of(facetName: FacetName, facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType?): OptionalNumberTemplateFacet {
                return OptionalNumberTemplateFacet(facetName, facetCalculationFunction)
            }
        }
    }


    fun ofMandatoryInput(facetName: String): MandatoryNumberInputFacet {
        return MandatoryNumberInputFacet.of(FacetName.of(facetName))
    }

    fun ofOptionalInput(facetName: String): OptionalNumberInputFacet {
        return OptionalNumberInputFacet.of(FacetName.of(facetName))
    }

    fun ofMandatoryInputAndTemplate(facetName: String): MandatoryNumberInputAndTemplateFacet
        = MandatoryNumberInputAndTemplateFacet.of(FacetName.of(facetName))

    fun ofOptionalTemplate(facetName: String, facetCalculationFunction: (ConceptModelNodeCalculationData) -> NumberFacetKotlinType?): OptionalNumberTemplateFacet
        = OptionalNumberTemplateFacet.of(FacetName.of(facetName), facetCalculationFunction)

}
