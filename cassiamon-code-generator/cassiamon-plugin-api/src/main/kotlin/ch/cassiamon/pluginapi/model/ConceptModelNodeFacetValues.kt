package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.*

interface ConceptModelNodeFacetValues {

    fun allFacetNames(): Set<FacetName>

    fun asString(facetDescriptor: ManualMandatoryTextFacetDescriptor): String
    fun asString(facetDescriptor: ManualOptionalTextFacetDescriptor): String?

    fun asInt(facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor): Int
    fun asInt(facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor): Int?

    fun asReferencedConceptModelNode(facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor): ConceptModelNode
    fun asReferencedConceptModelNode(facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor): ConceptModelNode?

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
