package ch.cassiamon.api.process.schema


interface FacetSchema {
    val facetName: FacetName
    val facetType: FacetTypeEnum
    val mandatory: Boolean
    val referencingConcept: ConceptName?
}
