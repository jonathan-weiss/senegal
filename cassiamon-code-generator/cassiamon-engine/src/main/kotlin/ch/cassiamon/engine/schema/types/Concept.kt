package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


interface Concept {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val facets: List<Facet>
    fun hasFacet(facetName: FacetName): Boolean {
        return facets
            .firstOrNull { it.facetName == facetName } != null
    }

    fun hasManualFacet(facetName: FacetName): Boolean {
        return facets
            .filter { it.isManualFacet() }
            .firstOrNull { it.facetName == facetName } != null
    }
}
