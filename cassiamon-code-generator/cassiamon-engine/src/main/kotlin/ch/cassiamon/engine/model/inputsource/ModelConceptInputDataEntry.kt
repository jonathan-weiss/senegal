package ch.cassiamon.engine.model.inputsource

import ch.cassiamon.engine.model.types.ConceptIdentifier
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

data class ModelConceptInputDataEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val facetValues: Map<FacetName, FacetValue>
)
