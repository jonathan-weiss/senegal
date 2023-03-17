package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.model.InputFacetValueAccess
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetDescriptor
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNodePool
import ch.cassiamon.pluginapi.model.ConceptModelNodeTemplateFacetValues
import ch.cassiamon.pluginapi.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.UnknownFacetNameFoundModelException
import ch.cassiamon.pluginapi.model.exceptions.WrongTypeForFacetValueModelException
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

class DirectAccessConceptModelNodeTemplateFacetValues(
    private val schema: Schema,
    private val infiniteLoopDetector: InfiniteLoopDetector,
    private val nodePool: ConceptModelNodePool,
    private val conceptName: ConceptName,
    private val conceptIdentifier: ConceptIdentifier,
    private val manualFacetValues: InputFacetValueAccess,
): ConceptModelNodeTemplateFacetValues {

    private val templateFacetSchemas: Map<FacetName, TemplateFacetSchema<*>> = schema.conceptByConceptName(conceptName).templateFacets.associateBy { it.templateFacet.facetName }
    private val materializedFacetValues: MutableMap<FacetName, Any?> = mutableMapOf()
    private val materializedFacets: MutableSet<FacetName> = mutableSetOf() // separate set to support optional values with null
    override fun allTemplateFacetNames(): Set<FacetName> {
        return schema.conceptByConceptName(conceptName).templateFacets.map { it.templateFacet.facetName }.toSet()
    }

    override fun <T> facetValue(facet: TemplateFacet<T>): T {
//        if(materializedFacets.contains(facetDescriptor.facetName)) {
//            return optionalFacetValue(facetDescriptor.facetName, String::class.java)
//        }
//
//        val schemaFacet = schemaFacetOf(facetDescriptor.facetName)
//        if(schemaFacet.facetDescriptor.isManualFacetValue) {
//            val manualFacetValue = manualFacetValues.getTextFacetValue(facetDescriptor)
//                ?: if(schemaFacet.facetDescriptor.isMandatoryFacetValue) {
//                    throw MissingFacetValueModelException(conceptName, conceptIdentifier, facetDescriptor.facetName)
//                } else {
//                    return null
//                }
//        }

        TODO("Not implemented, yet")
    }


    private fun checkFacetNameTypeValid(facetDescriptor: FacetDescriptor<*>) {
        val templateFacetSchema = templateFacetSchemaOf(facetDescriptor.facetName)

        if(templateFacetSchema.templateFacet.isMandatoryTemplateFacetValue != facetDescriptor.isMandatoryFacetValue) {
            throw InvalidFacetConfigurationModelException(conceptName, conceptIdentifier, facetDescriptor.facetName, "Facet value is mandatory.")
        }


    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


    private fun templateFacetSchemaOf(facetName: FacetName): TemplateFacetSchema<*> {
        return templateFacetSchemas[facetName] ?: throw UnknownFacetNameFoundModelException(conceptName, conceptIdentifier, facetName)
    }
    private inline fun <reified T: Any?> optionalFacetValue(facetName: FacetName, clazz: Class<T>): T? {
        val facetValue = materializedFacetValues[facetName] ?: return null

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

    private inline fun <reified T: Any> mandatoryFacetValue(facetName: FacetName, clazz: Class<T>): T {
        val facetValue = materializedFacetValues[facetName] ?: throw MissingFacetValueModelException(conceptName, conceptIdentifier, facetName)

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

}
