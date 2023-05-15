package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptModelNodeCalculationData

object TextFacets {

    class MandatoryTextInputFacet private constructor(override val facetName: FacetName)
        : MandatoryInputFacet<TextFacetKotlinType> {

        override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
            get() = MandatoryTextFacetType

        fun facetValue(value: TextFacetKotlinType): InputFacetValue<TextFacetKotlinType> {
            return InputFacetValue(this, value)
        }

        companion object {
            fun of(facetName: FacetName): MandatoryTextInputFacet {
                return MandatoryTextInputFacet(facetName)
            }
        }
    }

    class MandatoryTextTemplateFacet private constructor(
        override val facetName: FacetName,
        override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType
    )
        : MandatoryTemplateFacet<TextFacetKotlinType> {

        override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
            get() = MandatoryTextFacetType

        companion object {
            fun of(facetName: FacetName, facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType): MandatoryTextTemplateFacet {
                return MandatoryTextTemplateFacet(facetName, facetCalculationFunction)
            }
        }
    }

    class MandatoryTextInputAndTemplateFacet private constructor(override val facetName: FacetName, )
        : MandatoryInputAndTemplateFacet<TextFacetKotlinType, TextFacetKotlinType> {

        override val inputFacetType: MandatoryFacetType<TextFacetKotlinType>
            get() = MandatoryTextFacetType

        override val templateFacetType: MandatoryFacetType<TextFacetKotlinType>
            get() = MandatoryTextFacetType

        override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType
            get() = { it.inputFacetValues.facetValue(this) }

        fun facetValue(value: TextFacetKotlinType): InputFacetValue<TextFacetKotlinType> {
            return InputFacetValue(this, value)
        }

        companion object {
            fun of(facetName: FacetName): MandatoryTextInputAndTemplateFacet {
                return MandatoryTextInputAndTemplateFacet(facetName)
            }
        }
    }


    fun ofMandatoryInput(facetName: String): MandatoryTextInputFacet {
        return MandatoryTextInputFacet.of(FacetName.of(facetName))
    }

    fun ofMandatoryInputAndTemplate(facetName: String): MandatoryTextInputAndTemplateFacet {
        return MandatoryTextInputAndTemplateFacet.of(FacetName.of(facetName))
    }

    fun ofMandatoryTemplate(facetName: String, facetCalculationFunction: (ConceptModelNodeCalculationData) -> TextFacetKotlinType): MandatoryTextTemplateFacet
        = MandatoryTextTemplateFacet.of(FacetName.of(facetName), facetCalculationFunction)


}
