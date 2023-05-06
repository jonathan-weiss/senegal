package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.validator.CircularFacetDependencyDetector
import ch.cassiamon.engine.domain.Schema
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.ConceptModelNodeCalculationData
import ch.cassiamon.api.model.ConceptModelNodePool
import ch.cassiamon.api.model.InputFacetValueAccess

data class CalculationAndValidationData(
    val schema: Schema,
    val circularFacetDependencyDetector: CircularFacetDependencyDetector,
    val conceptModelNodePool: ConceptModelNodePool,
) {
    fun createConceptModelNodeCalculationData(conceptModelNode: ConceptModelNode, inputFacetValues: InputFacetValueAccess): ConceptModelNodeCalculationData {
        return ConceptModelNodeCalculationDataImpl(
            conceptModelNode = conceptModelNode,
            inputFacetValues = inputFacetValues,
            conceptModelNodePool = conceptModelNodePool,
        )
    }
}
