package ch.cassiamon.engine.model

import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.*
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

class DirectAccessConceptModelNodeTemplateFacetValues(
    private val calculationAndValidationData: CalculationAndValidationData,
    private val conceptModelNode: ConceptModelNode,
    private val inputFacetValues: InputFacetValueAccess
): ConceptModelNodeTemplateFacetValues {

    override fun allTemplateFacetNames(): Set<FacetName> {
        return calculationAndValidationData
            .schema.conceptByConceptName(conceptModelNode.conceptName).templateFacets
            .map { it.templateFacet.facetName }.toSet()
    }

    override fun <T> facetValue(templateFacet: TemplateFacet<T>): T {
        val templateFacetSchema: TemplateFacetSchema<T> = calculationAndValidationData.schema
            .conceptByConceptName(conceptModelNode.conceptName).templateFacets
            .first { it.templateFacet == templateFacet } as TemplateFacetSchema<T>


        val calculationData = calculationAndValidationData
            .createConceptModelNodeCalculationData(conceptModelNode, inputFacetValues)
        val facetValue = templateFacetSchema.facetCalculationFunction(calculationData)

        if (facetValue == null && templateFacet.isMandatoryTemplateFacetValue) {
           throw MissingFacetValueModelException(conceptModelNode.conceptName, conceptModelNode.conceptIdentifier, templateFacet.facetName)
        }

        return facetValue
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }

}
