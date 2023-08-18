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

    fun EntityConcept.primaryKeyField(): PrimaryKeyFieldConcept {
        return this.primaryKeys().first()
    }

    fun EntityConcept.kotlinIdClass(): String {
        return "${this.getName()}Id"
    }



    fun EntityField.type(): FieldDataType {
        return when(this) {
            is DataOnlyFieldConcept -> this.getType()
            is ReferenceToPrimaryKeyFieldConcept -> FieldDataType.UUID
            is PrimaryKeyFieldConcept -> FieldDataType.UUID
        }
    }

    fun EntityField.kotlinTypeAsString(): String {
        return when(this.type()) {
            FieldDataType.TEXT -> kotlinStringType
            FieldDataType.UUID -> kotlinStringType
            else -> throw RuntimeException("Unknown Kotlin Type for $this")
        }
    }

    fun EntityField.typescriptTypeAsString(): String {
        return when(this.type()) {
            FieldDataType.TEXT -> typescriptStringType
            FieldDataType.UUID -> typescriptStringType // TODO avoid that
            else -> throw RuntimeException("Unknown Typescript for $this")
        }
    }

    fun EntityField.sqlTypeAsString(): String {
        return when(this.type()) {
            FieldDataType.TEXT -> sqlStringType
            FieldDataType.UUID -> sqlUuidType
            else -> throw RuntimeException("Unknown SQL for $this")
        }
    }


}
