package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("DataOnlyField")
interface DataOnlyFieldConcept: EntityField {
    @Facet("FieldDataType")
    fun getType(): FieldDataType

}
