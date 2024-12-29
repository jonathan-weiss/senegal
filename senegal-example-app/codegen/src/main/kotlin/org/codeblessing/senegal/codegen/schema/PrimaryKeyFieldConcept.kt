package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.Concept

@Concept(facets = [
    EntityField.FieldName::class,
])
interface PrimaryKeyFieldConcept: EntityField {
    fun getParentEntity(): EntityConcept // TODO remove as soon as this is solved
}
