package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNode


class FacetForCalculatedMandatoryTextFacet (
    conceptName: ConceptName,
    facetName: FacetName,
    val facetCalculationFunction: (ConceptModelNode) -> String,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
)
