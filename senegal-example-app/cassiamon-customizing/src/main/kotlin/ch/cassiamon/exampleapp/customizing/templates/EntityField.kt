package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Facet

interface EntityField {
    @Facet("FieldName")
    fun getName(): String

    @Facet("FieldDataType")
    fun getType(): FieldDataType
}
