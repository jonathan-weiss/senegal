package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.pluginapi.schema.InputFacetSchema
import ch.cassiamon.pluginapi.model.facets.*

object XmlFacetValueConverter {
    fun convertString(inputFacetSchema: InputFacetSchema<*>, attributeValue: String): InputFacetValue<*> {

        return when(inputFacetSchema.inputFacet) {
            is MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is MandatoryNumberInputAndTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
            is MandatoryTextInputAndTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is MandatoryNumberInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
            is MandatoryTextInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue)
            is OptionalNumberInputFacet -> createInputFacetValue(inputFacetSchema.inputFacet, attributeValue.toLong())
        }
    }

    private fun <T> createInputFacetValue(inputFacet: InputFacet<T>, facetValue: T): InputFacetValue<T> {
        return InputFacetValue(inputFacet = inputFacet, facetValue = facetValue)
    }
}

