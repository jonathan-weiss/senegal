package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.Concept
import org.codeblessing.sourceamazing.schema.api.annotations.EnumFacet
import org.codeblessing.sourceamazing.schema.api.annotations.QueryFacetValue

@Concept([
    EntityField.FieldName::class,
    DataOnlyFieldConcept.DataType::class,
])
interface DataOnlyFieldConcept: EntityField {

    @EnumFacet(enumerationClass = FieldDataType::class)
    interface DataType

    @QueryFacetValue(DataType::class)
    fun getType(): FieldDataType
}
