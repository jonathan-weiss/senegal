package org.codeblessing.senegal.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept
import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet

@Concept("ReferenceToPrimaryKeyField")
interface ReferenceToPrimaryKeyFieldConcept: org.codeblessing.senegal.customizing.templates.EntityField {

    @Facet("ReferencedPrimaryKeyField")
    fun getReferencedPrimaryKeyField(): org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept
}
