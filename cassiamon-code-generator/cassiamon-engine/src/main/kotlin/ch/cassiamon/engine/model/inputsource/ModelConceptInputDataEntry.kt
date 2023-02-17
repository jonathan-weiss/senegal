package ch.cassiamon.engine.model.inputsource

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

data class ModelConceptInputDataEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    val facetValuesMap: Map<FacetName, FacetValue>
)
