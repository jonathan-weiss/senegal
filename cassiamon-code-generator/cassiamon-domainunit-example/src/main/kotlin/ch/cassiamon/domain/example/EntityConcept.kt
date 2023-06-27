package ch.cassiamon.domain.example

interface EntityConcept {

    fun entityName(): String
    fun entityAlternativeName(): String

    fun getEntityAttributes(): List<EntityAttributeConcept>
}
