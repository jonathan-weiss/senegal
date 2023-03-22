package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.validator.CircularFacetDependencyDetector
import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.*
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.ExceptionDuringTemplateFacetCalculationModelException
import ch.cassiamon.pluginapi.model.exceptions.ModelException
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

class ReactiveConceptModelNodeTemplateFacetValues(
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
            .conceptByConceptName(conceptModelNode.conceptName)
            .templateFacetSchemaOf(templateFacet)


        return calculateFacetValue(templateFacetSchema)
    }

    private fun <T> calculateFacetValue(templateFacetSchema: TemplateFacetSchema<T>): T {
        val templateFacet = templateFacetSchema.templateFacet
        val calculationData = calculationAndValidationData
            .createConceptModelNodeCalculationData(conceptModelNode, inputFacetValues)

        val calculationStep = calculationAndValidationData.circularFacetDependencyDetector.startFacetCalculation(
            conceptName = conceptModelNode.conceptName,
            conceptIdentifier = conceptModelNode.conceptIdentifier,
            facetName = templateFacet.facetName,
        )
        val facetValue = try {
            templateFacetSchema.facetCalculationFunction(calculationData)
        }
        catch (ex: ModelException) {
            throw ex
        }
        catch (ex: Exception) {
            throw ExceptionDuringTemplateFacetCalculationModelException(
                conceptName = conceptModelNode.conceptName,
                conceptIdentifier = conceptModelNode.conceptIdentifier,
                facetName = templateFacet.facetName,
                cause = ex)
        } finally {
            calculationAndValidationData.circularFacetDependencyDetector.endFacetCalculation(calculationStep)
        }

        if (facetValue == null && templateFacet.isMandatoryTemplateFacetValue) {
            throw MissingFacetValueModelException(conceptModelNode.conceptName, conceptModelNode.conceptIdentifier, templateFacet.facetName)
        }

        return facetValue
    }

    override fun get(key: String): Any? {


        TODO("Not yet implemented")
    }

}
