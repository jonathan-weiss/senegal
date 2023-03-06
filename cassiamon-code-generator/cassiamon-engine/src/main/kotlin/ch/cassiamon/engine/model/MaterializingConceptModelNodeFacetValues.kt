package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.types.*
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
    private val manualFacetValues: Map<FacetName, FacetValue>,
): ConceptModelNodeFacetValues {

    private val schemaFacets: Map<FacetName, Facet> = schema.conceptByConceptName(conceptName).facets.associateBy { it.facetName }
    private val materializedFacetValues: MutableMap<FacetName, FacetValue> = mutableMapOf()
    private val materializedFacets: MutableSet<FacetName> = mutableSetOf() // separate set to support optional values with null
    override fun allFacetNames(): Set<FacetName> {
        return schema.conceptByConceptName(conceptName).facets.map { it.facetName }.toSet()
    }

    override fun asString(facetName: NameOfMandatoryTextFacet): String {
        if(materializedFacets.contains(facetName)) {
            return mandatoryFacetValue(facetName, TextFacetValue::class.java).text
        }
        TODO("Not yet implemented")
    }

    override fun asString(facetName: NameOfOptionalTextFacet): String? {
        if(materializedFacets.contains(facetName)) {
            return optionalFacetValue(facetName, TextFacetValue::class.java)?.text
        }

        val schemaFacet = schemaFacetOf(facetName)
        if(schemaFacet.isManualFacet) {
            val manualFacetValue = manualFacetValues[facetName]
                ?: if(schemaFacet.isMandatoryFacet) {
                    throw MissingFacetValueModelException(conceptName, conceptIdentifier, facetName)
                } else {
                    return null
                }



        }


        TODO("Not yet implemented")
    }

    private fun checkFacetNameTypeValid(facetName: FacetName) {
        val schemaFacet = schemaFacetOf(facetName)

        if(schemaFacet.isMandatoryFacet != facetName.isMandatoryFacetValue) {
            throw InvalidFacetConfigurationModelException(conceptName, conceptIdentifier, facetName, "Facet value is mandatory.")
        }


    }

    override fun asInt(facetName: NameOfMandatoryIntegerNumberFacet): Int {
        TODO("Not yet implemented")
    }

    override fun asInt(facetName: NameOfOptionalIntegerNumberFacet): Int? {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetName: NameOfMandatoryConceptReferenceFacet): ConceptModelNode {
        TODO("Not yet implemented")
    }

    override fun asReferencedConceptModelNode(facetName: NameOfOptionalConceptReferenceFacet): ConceptModelNode? {
        TODO("Not yet implemented")
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }

    private fun schemaFacetOf(facetName: FacetName): Facet {
        return schemaFacets[facetName] ?: throw UnknownFacetNameFoundModelException(conceptName, conceptIdentifier, facetName)
    }
    private inline fun <reified T: FacetValue> optionalFacetValue(facetName: FacetName, clazz: Class<T>): T? {
        val facetValue = materializedFacetValues[facetName] ?: return null

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

    private inline fun <reified T: FacetValue> mandatoryFacetValue(facetName: FacetName, clazz: Class<T>): T {
        val facetValue = materializedFacetValues[facetName] ?: throw MissingFacetValueModelException(conceptName, conceptIdentifier, facetName)

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

}
