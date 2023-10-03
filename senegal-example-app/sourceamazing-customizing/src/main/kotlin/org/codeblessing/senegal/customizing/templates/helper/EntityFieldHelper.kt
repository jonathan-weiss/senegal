package org.codeblessing.senegal.customizing.templates.helper


object EntityFieldHelper {
    private val kotlinUuidType = "java.util.UUID"
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

    fun org.codeblessing.senegal.customizing.templates.EntityConcept.primaryKeyField(): org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept {
        return this.primaryKeys().first()
    }

    fun org.codeblessing.senegal.customizing.templates.EntityConcept.kotlinIdClass(): String {
        return "${this.getName()}Id"
    }



    fun org.codeblessing.senegal.customizing.templates.EntityField.type(): org.codeblessing.senegal.customizing.templates.FieldDataType {
        return when(this) {
            is org.codeblessing.senegal.customizing.templates.DataOnlyFieldConcept -> this.getType()
            is org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept -> org.codeblessing.senegal.customizing.templates.FieldDataType.UUID
            is org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept -> org.codeblessing.senegal.customizing.templates.FieldDataType.UUID
        }
    }

    fun org.codeblessing.senegal.customizing.templates.EntityField.kotlinTypeAsString(): String {
        return when(this.type()) {
            org.codeblessing.senegal.customizing.templates.FieldDataType.TEXT -> kotlinStringType
            org.codeblessing.senegal.customizing.templates.FieldDataType.UUID -> kotlinUuidType
            else -> throw RuntimeException("Unknown Kotlin Type for $this")
        }
    }

    fun org.codeblessing.senegal.customizing.templates.EntityField.typescriptTypeAsString(): String {
        return when(this.type()) {
            org.codeblessing.senegal.customizing.templates.FieldDataType.TEXT -> typescriptStringType
            org.codeblessing.senegal.customizing.templates.FieldDataType.UUID -> typescriptStringType // TODO avoid that
            else -> throw RuntimeException("Unknown Typescript for $this")
        }
    }

    fun org.codeblessing.senegal.customizing.templates.EntityField.sqlTypeAsString(): String {
        return when(this.type()) {
            org.codeblessing.senegal.customizing.templates.FieldDataType.TEXT -> sqlStringType
            org.codeblessing.senegal.customizing.templates.FieldDataType.UUID -> sqlUuidType
            else -> throw RuntimeException("Unknown SQL for $this")
        }
    }


}
