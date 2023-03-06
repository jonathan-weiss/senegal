package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

abstract class CalculatedFacet(
    conceptName: ConceptName,
    facetName: FacetName,
): Facet(
    conceptName = conceptName,
    facetName = facetName,
) {
    override val isCalculatedFacet: Boolean
        get() = true
}

