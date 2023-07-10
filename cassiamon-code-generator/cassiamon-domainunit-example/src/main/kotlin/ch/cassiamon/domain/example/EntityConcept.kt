package ch.cassiamon.domain.example

import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("Entity")
interface EntityConcept {

    @Facet("Name")
    fun entityName(): String
    @Facet("AlternativeName", mandatory = false)
    fun entityAlternativeName(): String

    @ChildConcepts(EntityAttributeConcept::class)
    fun getEntityAttributes(): List<EntityAttributeConcept>
}
