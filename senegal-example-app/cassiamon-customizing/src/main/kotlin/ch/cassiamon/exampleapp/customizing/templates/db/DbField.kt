package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.DataOnlyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class DbField(private val model: EntityField, private val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val sqlStringType = "VARCHAR(255)"
    private val sqlIntType = "INTEGER"
    private val sqlBooleanType = "BOOLEAN"

    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = EntityFieldHelper.type(model)

    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = when(entityAttributeType) {
        FieldDataType.TEXT -> sqlStringType
        else -> throw RuntimeException("Unknown SQL Type for $entityAttributeType")
    }
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = when(entityAttributeType) {
        FieldDataType.TEXT -> kotlinStringType
        else -> throw RuntimeException("Unknown Kotlin Type for $entityAttributeType")
    }
}
