package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

sealed class Facet constructor(
    val conceptName: ConceptName,
    val facetName: FacetName,
    val facetType: FacetType,
    val facetDependencies: Set<FacetName>,
) {
    fun isCalculatedFacet(): Boolean {
        return this is CalculatedFacet
    }

    fun isManualFacet(): Boolean {
        return this is ManualFacet
    }

}
