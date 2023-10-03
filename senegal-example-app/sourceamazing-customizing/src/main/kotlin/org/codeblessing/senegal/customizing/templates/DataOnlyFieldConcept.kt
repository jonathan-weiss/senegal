package org.codeblessing.senegal.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept
import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet

@Concept("DataOnlyField")
interface DataOnlyFieldConcept: org.codeblessing.senegal.customizing.templates.EntityField {
    @Facet("FieldDataType")
    fun getType(): org.codeblessing.senegal.customizing.templates.FieldDataType

}
