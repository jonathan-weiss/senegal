package org.codeblessing.senegal.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConcepts
import org.codeblessing.sourceamazing.api.process.schema.annotations.Schema

@Schema
interface EntitiesSchema {
    @ChildConcepts(org.codeblessing.senegal.customizing.templates.EntityConcept::class)
    fun entities(): List<org.codeblessing.senegal.customizing.templates.EntityConcept>
}
