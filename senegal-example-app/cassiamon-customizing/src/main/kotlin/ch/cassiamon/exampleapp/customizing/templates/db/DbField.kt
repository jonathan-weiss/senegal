package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityAttributeConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class DbField(private val model: EntityAttributeConcept, private val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val sqlStringType = "VARCHAR(255)"
    private val sqlIntType = "INTEGER"
    private val sqlBooleanType = "BOOLEAN"

    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: String = model.getType()

    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = when(entityAttributeType) {
        "Text" -> sqlStringType
        "IntegerNumber" -> sqlIntType
        "Boolean" -> sqlBooleanType
        else -> throw RuntimeException("Unknown SQL Type for $entityAttributeType")
    }
    val jpaFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jpaFieldType = when(entityAttributeType) {
        "Text" -> kotlinStringType
        "IntegerNumber" -> kotlinIntType
        "Boolean" -> kotlinBooleanType
        else -> throw RuntimeException("Unknown Kotlin Type for $entityAttributeType")
    }



}
