package ch.cassiamon.api.process.schema

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName


interface ConceptSchema {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val facets: List<FacetSchema>

    val facetNames: List<FacetName>
        get() = facets.map { it.facetName }.toList()

    fun hasFacet(facetName: FacetName): Boolean {
        return facetNames.contains(facetName)
    }
}
