package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.*

interface ConceptModelNodeFacetValues {

    fun allFacetNames(): Set<FacetName>

    fun asString(facetName: NameOfMandatoryTextFacet): String
    fun asString(facetName: NameOfOptionalTextFacet): String?

    fun asInt(facetName: NameOfMandatoryIntegerNumberFacet): Int
    fun asInt(facetName: NameOfOptionalIntegerNumberFacet): Int?

    fun asReferencedConceptModelNode(facetName: NameOfMandatoryConceptReferenceFacet): ConceptModelNode
    fun asReferencedConceptModelNode(facetName: NameOfOptionalConceptReferenceFacet): ConceptModelNode?

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
