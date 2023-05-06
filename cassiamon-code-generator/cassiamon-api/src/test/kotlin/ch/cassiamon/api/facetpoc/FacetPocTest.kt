package ch.cassiamon.api.facetpoc

import ch.cassiamon.api.FacetName
import org.junit.jupiter.api.Test

internal class FacetPocTest {


    // facet types
    sealed interface PocFacetType<C> {
        val isMandatory: Boolean
    }
    sealed interface PocMandatoryFacetType<C>: PocFacetType<C> {
        override val isMandatory: Boolean
            get() = true
    }
    sealed interface PocOptionalFacetType<C>: PocFacetType<C> {
        override val isMandatory: Boolean
            get() = false

    }
    object PocMandatoryTextFacetType: PocMandatoryFacetType<String>
    object PocOptionalTextFacetType: PocOptionalFacetType<String?>
    object PocMandatoryNumberFacetType: PocMandatoryFacetType<Long>
    object PocOptionalNumberFacetType: PocOptionalFacetType<Long?>


    // input/template/combined facets

    sealed interface PocInputFacet<I> {
        val facetName: FacetName
        val inputFacetType: PocFacetType<I>
        val isInputFacetMandatory: Boolean
            get() = inputFacetType.isMandatory
    }

    sealed interface PocTemplateFacet<T>  {
        val facetName: FacetName
        val templateFacetType: PocFacetType<T>
        val isTemplateFacetMandatory: Boolean
            get() = templateFacetType.isMandatory
    }

    sealed interface PocCombinedFacet<I, T>: PocInputFacet<I>, PocTemplateFacet<T>

    // real input facets

    class PocMandatoryTextInputFacet(override val facetName: FacetName): PocInputFacet<String> {
        override val inputFacetType: PocFacetType<String>
            get() = PocMandatoryTextFacetType
    }

    class PocOptionalTextInputFacet(override val facetName: FacetName): PocInputFacet<String?> {
        override val inputFacetType: PocFacetType<String?>
            get() = PocOptionalTextFacetType
    }

    class PocMandatoryNumberInputFacet(override val facetName: FacetName): PocInputFacet<Long> {
        override val inputFacetType: PocFacetType<Long>
            get() = PocMandatoryNumberFacetType
    }

    // real template facets
    class PocMandatoryTextTemplateFacet(override val facetName: FacetName): PocTemplateFacet<String> {
        override val templateFacetType: PocFacetType<String>
            get() = PocMandatoryTextFacetType
    }

    class PocOptionalTextTemplateFacet(override val facetName: FacetName): PocTemplateFacet<String?> {
        override val templateFacetType: PocFacetType<String?>
            get() = PocOptionalTextFacetType
    }

    // real combined facets


    class PocMandatoryTextInputAndTemplateFacet(override val facetName: FacetName): PocCombinedFacet<String, String> {
        override val inputFacetType: PocFacetType<String>
            get() = PocMandatoryTextFacetType

        override val templateFacetType: PocFacetType<String>
            get() = PocMandatoryTextFacetType
    }

    class PocMixedInputAndTemplateFacet(override val facetName: FacetName): PocCombinedFacet<String?, Long> {
        override val inputFacetType: PocFacetType<String?>
            get() = PocOptionalTextFacetType

        override val templateFacetType: PocFacetType<Long>
            get() = PocMandatoryNumberFacetType
    }

    @Test
    fun facetPoc() {

        val inputTextFacet = PocMandatoryTextInputFacet(FacetName.of("TextInputFacet"))
        val templateTextFacet = PocMandatoryTextTemplateFacet(FacetName.of("TextTemplateFacet"))
        val combinedTextFacet = PocMandatoryTextInputAndTemplateFacet(FacetName.of("TextCombinedFacet"))
        val mixedCombinedTextFacet = PocMixedInputAndTemplateFacet(FacetName.of("MixedCombinedFacet"))

        println("--------------")
        println("inputTextFacet")
        println("callInputFacet(inputTextFacet)")
        callInputFacet(inputTextFacet)

        println("--------------")
        println("templateTextFacet")
        println("callTemplateFacet(templateTextFacet)")
        callTemplateFacet(templateTextFacet)

        println("--------------")
        println("combinedTextFacet")
        println("callCombinedFacet(combinedTextFacet)")
        callCombinedFacet(combinedTextFacet)
        println("callInputFacet(combinedTextFacet)")
        callInputFacet(combinedTextFacet)
        println("callTemplateFacet(combinedTextFacet)")
        callTemplateFacet(combinedTextFacet)

        println("--------------")
        println("mixedCombinedTextFacet")
        println("callCombinedFacet(mixedCombinedTextFacet)")
        callCombinedFacet(mixedCombinedTextFacet)
        println("callInputFacet(mixedCombinedTextFacet)")
        callInputFacet(mixedCombinedTextFacet)
        println("callTemplateFacet(mixedCombinedTextFacet)")
        callTemplateFacet(mixedCombinedTextFacet)

    }

    private fun <I> callInputFacet(inputFacet: PocInputFacet<I>) {
        // input facet called

        when(inputFacet) {
            is PocMandatoryTextInputFacet -> println(" PocMandatoryTextInputFacet: $inputFacet")
            is PocMandatoryTextInputAndTemplateFacet -> println(" PocMandatoryTextInputAndTemplateFacet: $inputFacet")
            is PocMandatoryNumberInputFacet -> println(" PocMandatoryNumberInputFacet: $inputFacet")
            is PocOptionalTextInputFacet -> println(" PocOptionalTextInputFacet: $inputFacet")
            is PocMixedInputAndTemplateFacet -> println(" PocMixedInputAndTemplateFacet: $inputFacet")
        }

        when(inputFacet.inputFacetType) {
            is PocMandatoryNumberFacetType -> println(" - PocMandatoryNumberFacetType for $inputFacet (${inputFacet.inputFacetType})")
            is PocMandatoryTextFacetType -> println(" - PocMandatoryTextFacetType for $inputFacet (${inputFacet.inputFacetType})")
            is PocOptionalNumberFacetType -> println(" - PocOptionalNumberFacetType for $inputFacet (${inputFacet.inputFacetType})")
            is PocOptionalTextFacetType -> println(" - PocOptionalTextFacetType for $inputFacet (${inputFacet.inputFacetType})")
        }


    }

    private fun <T> callTemplateFacet(templateFacet: PocTemplateFacet<T>) {
        // template facet called

        when(templateFacet) {
            is PocMandatoryTextInputAndTemplateFacet -> println(" PocMandatoryTextInputAndTemplateFacet: $templateFacet")
            is PocMandatoryTextTemplateFacet -> println(" PocMandatoryTextTemplateFacet: $templateFacet")
            is PocOptionalTextTemplateFacet -> println(" PocOptionalTextTemplateFacet: $templateFacet")
            is PocMixedInputAndTemplateFacet -> println(" PocMixedInputAndTemplateFacet: $templateFacet")
        }

        when(templateFacet.templateFacetType) {
            is PocMandatoryNumberFacetType -> println(" - PocMandatoryNumberFacetType for $templateFacet (${templateFacet.templateFacetType})")
            is PocMandatoryTextFacetType -> println(" - PocMandatoryTextFacetType for $templateFacet (${templateFacet.templateFacetType})")
            is PocOptionalNumberFacetType -> println(" - PocOptionalNumberFacetType for $templateFacet (${templateFacet.templateFacetType})")
            is PocOptionalTextFacetType -> println(" - PocOptionalTextFacetType for $templateFacet (${templateFacet.templateFacetType})")
        }


    }

    private fun <I, T> callCombinedFacet(inputAndTemplateFacet: PocCombinedFacet<I, T>) {
        // input and template facet called

        when(inputAndTemplateFacet.inputFacetType) {
            is PocMandatoryNumberFacetType -> println(" - PocMandatoryNumberFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.inputFacetType})")
            is PocMandatoryTextFacetType -> println(" - PocMandatoryTextFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.inputFacetType})")
            is PocOptionalNumberFacetType -> println(" - PocOptionalNumberFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.inputFacetType})")
            is PocOptionalTextFacetType -> println(" - PocOptionalTextFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.inputFacetType})")
        }

        when(inputAndTemplateFacet.templateFacetType) {
            is PocMandatoryNumberFacetType -> println(" - PocMandatoryNumberFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.templateFacetType})")
            is PocMandatoryTextFacetType -> println(" - PocMandatoryTextFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.templateFacetType})")
            is PocOptionalNumberFacetType -> println(" - PocOptionalNumberFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.templateFacetType})")
            is PocOptionalTextFacetType -> println(" - PocOptionalTextFacetType for $inputAndTemplateFacet (${inputAndTemplateFacet.templateFacetType})")
        }

    }

}
