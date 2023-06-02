package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.exampleapp.customizing.templates.EntityAttributeConcept
import ch.cassiamon.tools.CaseUtil

data class KotlinModelField(private val model: EntityAttributeConcept, private val kotlinModelClass: KotlinModelClass) {
    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.name
    private val entityAttributeType: String = model.type

    val kotlinFieldName = CaseUtil.decapitalize(entityAttributeName)
    val kotlinFieldType = when(entityAttributeType) {
        "IntegerNumber" -> kotlinStringType
        "Text" -> kotlinIntType
        "Boolean" -> kotlinBooleanType
        else -> throw RuntimeException("Unknown Kotlin Type for $entityAttributeType")
    }



}
