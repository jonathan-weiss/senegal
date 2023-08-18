package ch.cassiamon.exampleapp.customizing.templates

object EntityFieldHelper {

    fun type(entityField: EntityField): FieldDataType {
        return when(entityField) {
            is DataOnlyFieldConcept -> entityField.getType()
            is ReferenceToPrimaryKeyFieldConcept -> FieldDataType.UUID
            is PrimaryKeyFieldConcept -> FieldDataType.TEXT
        }
    }

}
