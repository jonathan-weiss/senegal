package ch.cassiamon.api.model

interface ConceptModelNodeCalculationData {
    val conceptModelNode: ConceptModelNode

    val inputFacetValues: InputFacetValueAccess

    val conceptModelNodePool: ConceptModelNodePool
}
