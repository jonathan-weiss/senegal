package ch.cassiamon.api.model

import ch.cassiamon.api.*
import ch.cassiamon.api.model.facets.TemplateFacet

interface ConceptModelNodeTemplateFacetValues {

    fun <T> facetValue(templateFacet: TemplateFacet<T>): T

    fun allTemplateFacetNames(): Set<FacetName>

    operator fun get(key: String): Any? {
        return when(key) {
            "allFacetNames" -> allTemplateFacetNames().map { it.name }.toSet()
            else -> facetValue(key)
        }
    }
    fun facetValue(key: String): Any?


}
