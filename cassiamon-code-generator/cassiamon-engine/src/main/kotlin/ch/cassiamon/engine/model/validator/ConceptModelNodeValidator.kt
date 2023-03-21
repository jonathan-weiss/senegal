package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.exceptions.InvalidTemplateFacetConfigurationModelException
import ch.cassiamon.pluginapi.model.facets.*

object ConceptModelNodeValidator {

    fun validate(conceptSchema: ConceptSchema, conceptModelNode: ConceptModelNode) {
        validateConceptModelNode(conceptModelNode)
        val templateFacetSchemas = conceptSchema.templateFacets

        for(templateFacetSchema in templateFacetSchemas) {
            val facetValue = conceptModelNode.templateFacetValues.facetValue(templateFacetSchema.templateFacet)
            validateValueAgainstTemplateFacetSchema(templateFacetSchema, facetValue, conceptModelNode)
        }
    }

    private fun validateConceptModelNode(conceptModelNode: ConceptModelNode) {
        // no further validation for input facet values, already done
        // no further validation for parent and child nodes necessary, already done
        conceptModelNode.parent()
        conceptModelNode.allChildren()
        conceptModelNode.templateFacetValues.allTemplateFacetNames()

    }

    private fun validateValueAgainstTemplateFacetSchema(templateFacetSchema: TemplateFacetSchema<*>, facetValue: Any?, conceptModelNode: ConceptModelNode) {
        when(val templateFacet = templateFacetSchema.templateFacet) {
            is MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
            is MandatoryNumberInputAndTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
            is MandatoryTextInputAndTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
            is OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
            is MandatoryTextTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
            is OptionalNumberTemplateFacet -> validateValueAgainstTemplateFacet(templateFacet, facetValue, conceptModelNode)
        }
    }

    private inline fun <reified T> validateValueAgainstTemplateFacet(templateFacet: TemplateFacet<T>, facetValue: Any?, conceptModelNode: ConceptModelNode) {
        if(facetValue == null && templateFacet.isMandatoryTemplateFacetValue) {
            throw InvalidTemplateFacetConfigurationModelException(
                conceptName = conceptModelNode.conceptName,
                conceptIdentifier = conceptModelNode.conceptIdentifier,
                facetName = templateFacet.facetName,
                reason = "Value of mandatory facet is null."
            )
        }

        if(facetValue == null) {
            return
        }

        if(facetValue !is T) {
            val expectedClass = T::class
            val actualClass = facetValue::class

            throw InvalidTemplateFacetConfigurationModelException(
                conceptName = conceptModelNode.conceptName,
                conceptIdentifier = conceptModelNode.conceptIdentifier,
                facetName = templateFacet.facetName,
                reason = "Facet value has wrong type: Expected was '$expectedClass' but was '$actualClass'"
            )
        }
    }
}
