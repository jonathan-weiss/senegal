package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.QueryFacetValue
import org.codeblessing.sourceamazing.schema.api.annotations.StringFacet

sealed interface EntityField {
    @StringFacet
    interface FieldName


    @QueryFacetValue(FieldName::class)
    fun getName(): String


    // fun getParentEntity(): EntityConcept
}
