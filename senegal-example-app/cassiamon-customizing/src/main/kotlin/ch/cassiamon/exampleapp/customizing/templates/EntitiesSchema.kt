package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Schema

@Schema
interface EntitiesSchema {
    @ChildConcepts(EntityConcept::class)
    fun entities(): List<EntityConcept>
}
