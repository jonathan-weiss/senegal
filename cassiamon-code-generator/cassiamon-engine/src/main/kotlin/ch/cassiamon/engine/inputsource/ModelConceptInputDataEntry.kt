package ch.cassiamon.engine.inputsource

import ch.cassiamon.api.model.InputFacetValueAccess
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.ConceptName

data class ModelConceptInputDataEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    val inputFacetValueAccess: InputFacetValueAccess,
)
