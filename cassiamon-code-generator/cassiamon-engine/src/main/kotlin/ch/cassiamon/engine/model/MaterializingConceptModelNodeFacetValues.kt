package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.facets.ManualFacetValueAccess
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNodeFacetValues
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.UnknownFacetNameFoundModelException
import ch.cassiamon.pluginapi.model.exceptions.WrongTypeForFacetValueModelException

class MaterializingConceptModelNodeFacetValues(
    private val schema: Schema,
    private val infiniteLoopDetector: InfiniteLoopDetector,
    private val nodePool: ConceptModelNodePool,
    private val conceptName: ConceptName,
    private val conceptIdentifier: ConceptIdentifier,
    private val manualFacetValues: ManualFacetValueAccess,
): ConceptModelNodeFacetValues {

    private val schemaFacets: Map<FacetName, FacetSchema<*>> = schema.conceptByConceptName(conceptName).facets.associateBy { it.facetDescriptor.facetName }
    private val materializedFacetValues: MutableMap<FacetName, Any?> = mutableMapOf()
    private val materializedFacets: MutableSet<FacetName> = mutableSetOf() // separate set to support optional values with null
    override fun allFacetNames(): Set<FacetName> {
        return schema.conceptByConceptName(conceptName).facets.map { it.facetDescriptor.facetName }.toSet()
    }

    override fun asString(facetDescriptor: ManualMandatoryTextFacetDescriptor): String {
        if(materializedFacets.contains(facetDescriptor.facetName)) {
            return mandatoryFacetValue(facetDescriptor.facetName, String::class.java)
        }
        TODO("Not yet implemented")
    }

    override fun asString(facetDescriptor: ManualOptionalTextFacetDescriptor): String? {
        if(materializedFacets.contains(facetDescriptor.facetName)) {
            return optionalFacetValue(facetDescriptor.facetName, String::class.java)
        }

        val schemaFacet = schemaFacetOf(facetDescriptor.facetName)
        if(schemaFacet.facetDescriptor.isManualFacetValue) {
            val manualFacetValue = manualFacetValues.getTextFacetValue(facetDescriptor)
                ?: if(schemaFacet.facetDescriptor.isMandatoryFacetValue) {
                    throw MissingFacetValueModelException(conceptName, conceptIdentifier, facetDescriptor.facetName)
                } else {
                    return null
                }



        }


        TODO("Not yet implemented")
    }

    private fun checkFacetNameTypeValid(facetDescriptor: FacetDescriptor<*>) {
        val schemaFacet = schemaFacetOf(facetDescriptor.facetName)

        if(schemaFacet.facetDescriptor.isMandatoryFacetValue != facetDescriptor.isMandatoryFacetValue) {
            throw InvalidFacetConfigurationModelException(conceptName, conceptIdentifier, facetDescriptor.facetName, "Facet value is mandatory.")
        }


    }

    override fun asInt(facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun asInt(facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor): Int? {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor): ConceptModelNode {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor): ConceptModelNode? {
        TODO("Not yet implemented")
    }

    override fun asString(facetDescriptor: CalculatedMandatoryTextFacetDescriptor): String {
        TODO("Not yet implemented")
    }

    override fun asString(facetDescriptor: CalculatedOptionalTextFacetDescriptor): String? {
        TODO("Not yet implemented")
    }

    override fun asInt(facetDescriptor: CalculatedMandatoryIntegerNumberFacetDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun asInt(facetDescriptor: CalculatedOptionalIntegerNumberFacetDescriptor): Int? {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetDescriptor: CalculatedMandatoryConceptReferenceFacetDescriptor): ConceptModelNode {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetDescriptor: CalculatedOptionalConceptReferenceFacetDescriptor): ConceptModelNode? {
        TODO("Not yet implemented")
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }

    private fun schemaFacetOf(facetName: FacetName): FacetSchema<*> {
        return schemaFacets[facetName] ?: throw UnknownFacetNameFoundModelException(conceptName, conceptIdentifier, facetName)
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
