package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

abstract class ManualFacet(
    conceptName: ConceptName,
    facetName: FacetName,
    facetDependencies: Set<FacetName>,
): Facet(
    conceptName = conceptName,
    facetName = facetName,
    facetDependencies = facetDependencies,
) {
    override val isCalculatedFacet: Boolean
        get() = !isManualFacet
    override val isManualFacet: Boolean
        get() = true

}
