package ch.cassiamon.api.schema

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.*


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
