package ch.cassiamon.api.schema

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.*


interface ConceptSchema {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val facetNames: List<FacetName>
    val facets: List<FacetSchema>
    @Deprecated("Old style") val inputFacets: List<InputFacetSchema<*>>
    @Deprecated("Old style") val templateFacets: List<TemplateFacetSchema<*>>
    @Deprecated("Old style") fun hasInputFacet(facetName: FacetName): Boolean {
        return inputFacets
            .any { it.inputFacet.facetName == facetName }
    }

    @Deprecated("Old style") fun hasTemplateFacet(facetName: FacetName): Boolean {
        return templateFacets
            .any { it.templateFacet.facetName == facetName }
    }

    fun hasFacet(facetName: FacetName): Boolean {
        return facetNames.contains(facetName)
    }

    @Deprecated("Old style") fun <T> templateFacetSchemaOf(templateFacet: TemplateFacet<T>): TemplateFacetSchema<T>;


}
