package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Facet
import ch.cassiamon.api.process.schema.annotations.ParentConcept

sealed interface EntityField {
    @Facet("FieldName")
    fun getName(): String

    @ParentConcept
    fun getParentEntity(): EntityConcept
}
