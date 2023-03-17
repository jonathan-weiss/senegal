package ch.cassiamon.engine.inputsource

import ch.cassiamon.pluginapi.model.InputFacetValueAccess
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.ConceptName

data class ModelConceptInputDataEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    val inputFacetValueAccess: InputFacetValueAccess,
)
