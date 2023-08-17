package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.exampleapp.customizing.templates.DataOnlyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.tools.CaseUtil

data class KotlinModelField(private val model: EntityField, private val kotlinModelClass: KotlinModelClass) {
    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = model.getType()

    val kotlinFieldName = CaseUtil.decapitalize(entityAttributeName)
    val kotlinFieldType = when(entityAttributeType) {
        FieldDataType.TEXT -> kotlinStringType
        else -> throw RuntimeException("Unknown Kotlin Type for $entityAttributeType")
    }



}
