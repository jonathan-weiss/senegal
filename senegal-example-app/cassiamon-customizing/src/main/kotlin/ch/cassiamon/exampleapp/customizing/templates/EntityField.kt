package ch.cassiamon.exampleapp.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet
import org.codeblessing.sourceamazing.api.process.schema.annotations.ParentConcept

sealed interface EntityField {
    @Facet("FieldName")
    fun getName(): String

    @ParentConcept
    fun getParentEntity(): EntityConcept
}
