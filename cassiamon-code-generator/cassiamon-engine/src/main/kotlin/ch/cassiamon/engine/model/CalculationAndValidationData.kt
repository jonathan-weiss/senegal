package ch.cassiamon.engine.model

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.ConceptModelNodePool
import ch.cassiamon.pluginapi.model.InputFacetValueAccess

data class CalculationAndValidationData(
    val schema: Schema,
    val infiniteLoopDetector: InfiniteLoopDetector,
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
