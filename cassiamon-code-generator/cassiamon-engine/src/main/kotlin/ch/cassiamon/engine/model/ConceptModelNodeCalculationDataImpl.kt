package ch.cassiamon.engine.model

import ch.cassiamon.api.model.InputFacetValueAccess
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.ConceptModelNodeCalculationData
import ch.cassiamon.api.model.ConceptModelNodePool

data class ConceptModelNodeCalculationDataImpl(
    override val conceptModelNode: ConceptModelNode,
    override val inputFacetValues: InputFacetValueAccess,
    override val conceptModelNodePool: ConceptModelNodePool,
): ConceptModelNodeCalculationData
