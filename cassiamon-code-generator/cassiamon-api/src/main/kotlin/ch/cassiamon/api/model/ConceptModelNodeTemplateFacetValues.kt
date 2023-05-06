package ch.cassiamon.api.model

import ch.cassiamon.api.*
import ch.cassiamon.api.model.facets.TemplateFacet

interface ConceptModelNodeTemplateFacetValues {

    fun <T> facetValue(templateFacet: TemplateFacet<T>): T

    fun allTemplateFacetNames(): Set<FacetName>

    /**
    Support for template engines
    TODO document which keys are allowed
     */
    operator fun get(key: String): Any?;


}
