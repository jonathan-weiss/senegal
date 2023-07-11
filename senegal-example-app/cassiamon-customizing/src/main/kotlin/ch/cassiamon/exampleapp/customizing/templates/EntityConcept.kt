package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("Entity")
interface EntityConcept {

    @Facet("EntityName")
    fun getName(): String

    @ChildConcepts(EntityAttributeConcept::class)
    fun entityAttributes(): List<EntityAttributeConcept>
}
