package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

interface ConceptModelNodeTemplateFacetValues {

    fun <T> facetValue(facet: TemplateFacet<T>): T

    fun allTemplateFacetNames(): Set<FacetName>

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
