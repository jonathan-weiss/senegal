package ch.cassiamon.pluginapi.template

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier

interface TemplateNodeFacetValues {

    fun asString(facetName: FacetName): String

    fun asInt(facetName: FacetName): Int

    fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier
    fun asReferencedTemplateNode(facetName: FacetName): TemplateNode

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
