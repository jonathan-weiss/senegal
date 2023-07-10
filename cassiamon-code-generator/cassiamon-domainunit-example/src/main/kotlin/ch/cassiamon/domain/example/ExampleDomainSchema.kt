package ch.cassiamon.domain.example

import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Schema

@Schema
interface ExampleDomainSchema {

    @ChildConcepts(EntityConcept::class)
    fun getEntityConcepts(): List<EntityConcept>
}
