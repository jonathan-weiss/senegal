package ch.cassiamon.exampleapp.customizing.templates.helper

import ch.cassiamon.exampleapp.customizing.templates.*

object EntityFieldHelper {
    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val typescriptStringType = "string"
    private val typescriptIntType = "number"
    private val typescriptBooleanType = "boolean"

    private val sqlUuidType = "UUID"
    private val sqlStringType = "VARCHAR(255)"
    private val sqlIntType = "INTEGER"
    private val sqlBooleanType = "BOOLEAN"


    fun type(entityField: EntityField): FieldDataType {
        return when(entityField) {
            is DataOnlyFieldConcept -> entityField.getType()
            is ReferenceToPrimaryKeyFieldConcept -> FieldDataType.UUID
            is PrimaryKeyFieldConcept -> FieldDataType.TEXT
        }
    }

    fun kotlinTypeAsString(entityField: EntityField): String {
        return when(type(entityField)) {
            FieldDataType.TEXT -> kotlinStringType
            else -> throw RuntimeException("Unknown Kotlin Type for $entityField")
        }
    }

    fun typescriptTypeAsString(entityField: EntityField): String {
        return when(type(entityField)) {
            FieldDataType.TEXT -> typescriptStringType
            FieldDataType.UUID -> typescriptStringType // TODO avoid that
            else -> throw RuntimeException("Unknown Typescript for $entityField")
        }
    }

    fun sqlTypeAsString(entityField: EntityField): String {
        return when(type(entityField)) {
            FieldDataType.TEXT -> sqlStringType
            FieldDataType.UUID -> sqlUuidType
            else -> throw RuntimeException("Unknown SQL for $entityField")
        }
    }


}
