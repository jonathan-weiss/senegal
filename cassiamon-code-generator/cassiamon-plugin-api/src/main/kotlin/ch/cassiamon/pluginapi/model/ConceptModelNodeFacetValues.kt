package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.*

interface ConceptModelNodeFacetValues {

    fun allFacetNames(): Set<FacetName>

    fun asString(facetDescriptor: ManualMandatoryTextFacetDescriptor): String
    fun asString(facetDescriptor: ManualOptionalTextFacetDescriptor): String?

    fun asString(facetDescriptor: CalculatedMandatoryTextFacetDescriptor): String
    fun asString(facetDescriptor: CalculatedOptionalTextFacetDescriptor): String?


    fun asInt(facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor): Int
    fun asInt(facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor): Int?

    fun asInt(facetDescriptor: CalculatedMandatoryIntegerNumberFacetDescriptor): Int
    fun asInt(facetDescriptor: CalculatedOptionalIntegerNumberFacetDescriptor): Int?

    fun asReferencedConceptModelNode(facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor): ConceptModelNode
    fun asReferencedConceptModelNode(facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor): ConceptModelNode?
    fun asReferencedConceptModelNode(facetDescriptor: CalculatedMandatoryConceptReferenceFacetDescriptor): ConceptModelNode
    fun asReferencedConceptModelNode(facetDescriptor: CalculatedOptionalConceptReferenceFacetDescriptor): ConceptModelNode?

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
