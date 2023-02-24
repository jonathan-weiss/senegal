package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNodeFacetValues
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.ReferenceConceptModelNodeNotFoundException
import ch.cassiamon.pluginapi.model.exceptions.WrongTypeForFacetValueModelException

class MutableConceptModelNodeFacetValues(val facetValuesMap: Map<FacetName, FacetValue>):
    ConceptModelNodeFacetValues {

    private var referenceConceptModelNodeFacetValues: Map<FacetName, ConceptModelNode> = emptyMap()
    fun assignTemplateNodeFacetValues(referenceConceptModelNodeFacetValues: Map<FacetName, ConceptModelNode>) {
        this.referenceConceptModelNodeFacetValues = referenceConceptModelNodeFacetValues
    }



    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }

    override fun allFacetNames(): Set<FacetName> {
        return facetValuesMap.keys
    }

    override fun asString(facetName: NameOfMandatoryTextFacet): String {
        return (mandatoryFacetValue(facetName, TextFacetValue::class.java)).text
    }

    override fun asInt(facetName: NameOfMandatoryIntegerNumberFacet): Int {
        return (mandatoryFacetValue(facetName, IntegerNumberFacetValue::class.java)).number
    }

    override fun asString(facetName: NameOfOptionalTextFacet): String? {
        return (optionalFacetValue(facetName, TextFacetValue::class.java))?.text
    }

    override fun asInt(facetName: NameOfOptionalIntegerNumberFacet): Int? {
        return (optionalFacetValue(facetName, IntegerNumberFacetValue::class.java))?.number
    }

    override fun asReferencedConceptModelNode(facetName: NameOfOptionalConceptReferenceFacet): ConceptModelNode? {
        return referenceConceptModelNodeFacetValues[facetName]
    }

    override fun asReferencedConceptModelNode(facetName: NameOfMandatoryConceptReferenceFacet): ConceptModelNode {
        return referenceConceptModelNodeFacetValues[facetName]
        // TODO add information to node to the exception
            ?: throw ReferenceConceptModelNodeNotFoundException("Referenced template node for facet '$facetName' not found.")
    }


    private inline fun <reified T: FacetValue> optionalFacetValue(facetName: FacetName, clazz: Class<T>): T? {
        val facetValue = facetValuesMap[facetName] ?: return null

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

    private inline fun <reified T: FacetValue> mandatoryFacetValue(facetName: FacetName, clazz: Class<T>): T {
        val facetValue = facetValuesMap[facetName] ?: throw MissingFacetValueModelException(facetName.name)

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

    private fun calculateFacetAndUpdateValueMap(
        schemaFacet: Facet,
        conceptName: ConceptName,
        conceptIdentifier: ConceptIdentifier,
        facetValuesMap: MutableMap<FacetName, FacetValue>,
    ) {

        val facetName = schemaFacet.facetName
        val facetValues = MutableConceptModelNodeFacetValues(facetValuesMap)

        val facetRestrictedConceptNode = MutableConceptModelNode(
            conceptName = conceptName,
            conceptIdentifier = conceptIdentifier,
            facetValues = keyRestrictedFacetValue(facetValuesMap, schemaFacet),
        )

//        val newFacetValue: FacetValue = when(schemaFacet) {
//            is TextManualFacet -> TextFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asString(facetName)))
//            is TextCalculatedFacet -> TextFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
//            is IntegerNumberManualFacet -> IntegerNumberFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asInt(facetName)))
//            is IntegerNumberCalculatedFacet -> IntegerNumberFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
//            is ConceptReferenceManualFacet -> ConceptReferenceFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asConceptIdentifier(facetName)))
//            is ConceptReferenceCalculatedFacet -> ConceptReferenceFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
//            else -> throw IllegalStateException("The facet type $schemaFacet is not supported.")
//        }
//
//        facetValuesMap[facetName] = newFacetValue
    }

    private fun keyRestrictedFacetValue(facetValues: MutableMap<FacetName, FacetValue>,
                                        schemaFacet: Facet
    ): MutableConceptModelNodeFacetValues {
        return MutableConceptModelNodeFacetValues(facetValues) // TODO implement the restrictions
    }


}
