package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.FacetName

interface ConceptModelNodeFacetValues {

    fun allFacetNames(): Set<FacetName>

    fun asString(facetName: FacetName): String

    fun asInt(facetName: FacetName): Int

    fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier
    fun asReferencedConceptModelNode(facetName: FacetName): ConceptModelNode

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
