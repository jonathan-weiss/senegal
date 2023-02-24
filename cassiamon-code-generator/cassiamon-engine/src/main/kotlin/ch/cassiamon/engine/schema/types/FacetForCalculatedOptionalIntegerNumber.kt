package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNode


class FacetForCalculatedOptionalIntegerNumber (
    conceptName: ConceptName,
    facetName: FacetName,
    val facetCalculationFunction: (ConceptModelNode) -> Int?,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
)
