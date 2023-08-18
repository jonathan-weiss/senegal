package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Facet

sealed interface EntityField {
    @Facet("FieldName")
    fun getName(): String
}
