package ch.cassiamon.engine.inputsource

import ch.cassiamon.engine.model.facets.ManualFacetValueAccess
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.ConceptName

data class ModelConceptInputDataEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    val facetValueAccess: ManualFacetValueAccess,
)
