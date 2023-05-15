package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.model.facets.*

object XmlFacetValueConverter {
    fun convertString(inputFacetSchema: InputFacetSchema<*>, attributeValue: String): InputFacetValue<*> {

        return when(inputFacetSchema.inputFacet) {
            is ConceptFacets.MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is NumberFacets.MandatoryNumberInputAndTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
            is TextFacets.MandatoryTextInputAndTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is ConceptFacets.OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is NumberFacets.MandatoryNumberInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
            is TextFacets.MandatoryTextInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is NumberFacets.OptionalNumberInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
        }
    }

    private fun <T> createInputFacetValue(inputFacet: InputFacet<T>, facetValue: T): InputFacetValue<T> {
        return InputFacetValue(inputFacet = inputFacet, facetValue = facetValue)
    }
}

