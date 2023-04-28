package ch.cassiamon.pluginapi.schema

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.facets.*


interface ConceptSchema {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val inputFacets: List<InputFacetSchema<*>>
    val templateFacets: List<TemplateFacetSchema<*>>
    fun hasInputFacet(facetName: FacetName): Boolean {
        return inputFacets
            .any { it.inputFacet.facetName == facetName }
    }

    fun hasTemplateFacet(facetName: FacetName): Boolean {
        return templateFacets
            .any { it.templateFacet.facetName == facetName }
    }

    fun <T> templateFacetSchemaOf(templateFacet: TemplateFacet<T>): TemplateFacetSchema<T>;


}
