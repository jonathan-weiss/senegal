package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.QueryConcepts
import org.codeblessing.sourceamazing.schema.api.annotations.Schema

@Schema(concepts = [
    EntityConcept::class,
    PrimaryKeyFieldConcept::class,
    DataOnlyFieldConcept::class,
    ReferenceToPrimaryKeyFieldConcept::class,
])

interface EntitiesSchema {
    @QueryConcepts(conceptClasses = [EntityConcept::class])
    fun entities(): List<EntityConcept>
}
