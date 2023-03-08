package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.*

interface ConceptModelNodeFacetValues {

    fun <T> facetValue(facetDescriptor: FacetDescriptor<T>): T

    fun allFacetNames(): Set<FacetName>

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
