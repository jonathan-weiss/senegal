package ch.cassiamon.pluginapi.model

interface ConceptModelNodeCalculationData {
    val conceptModelNode: ConceptModelNode

    val inputFacetValues: InputFacetValueAccess

    val conceptModelNodePool: ConceptModelNodePool
}
