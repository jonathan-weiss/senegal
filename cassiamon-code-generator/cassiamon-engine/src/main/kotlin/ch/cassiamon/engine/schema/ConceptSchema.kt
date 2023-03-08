package ch.cassiamon.engine.schema

import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


interface ConceptSchema {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val facets: List<FacetSchema<*>>
    fun hasFacet(facetName: FacetName): Boolean {
        return facets.any { it.facetDescriptor.facetName == facetName }
    }

    fun hasManualFacet(facetName: FacetName): Boolean {
        return facets
            .filter { it.facetDescriptor.isManualFacetValue }
            .any { it.facetDescriptor.facetName == facetName }
    }
}
