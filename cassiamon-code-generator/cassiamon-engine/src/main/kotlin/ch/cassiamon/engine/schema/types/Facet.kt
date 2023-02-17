package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

sealed class Facet (
    val conceptName: ConceptName,
    val facetName: FacetName,
    val facetDependencies: Set<FacetName>,
) {

    abstract val facetType: FacetType
    abstract val isCalculatedFacet: Boolean
    abstract val isManualFacet: Boolean
}
