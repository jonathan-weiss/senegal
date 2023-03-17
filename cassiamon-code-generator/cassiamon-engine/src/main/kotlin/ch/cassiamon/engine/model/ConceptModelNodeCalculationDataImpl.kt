package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.model.InputFacetValueAccess
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.ConceptModelNodePool

data class ConceptModelNodeCalculationDataImpl(
    override val conceptModelNode: ConceptModelNode,
    override val inputFacetValues: InputFacetValueAccess,
    override val conceptModelNodePool: ConceptModelNodePool,
): ConceptModelNodeCalculationData
