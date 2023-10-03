package ch.cassiamon.exampleapp.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConcepts
import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConceptsWithCommonBaseInterface
import org.codeblessing.sourceamazing.api.process.schema.annotations.Schema

@Schema
interface EntitiesSchema {
    @ChildConcepts(EntityConcept::class)
    fun entities(): List<EntityConcept>
}
