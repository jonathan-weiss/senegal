package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.exampleapp.customizing.templates.DataOnlyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class RestModelField(private val model: EntityField, private val restModelClass: RestModelClass, val kotlinModelField: KotlinModelField) {
    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = EntityFieldHelper.type(model)

    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = when(entityAttributeType) {
        FieldDataType.TEXT -> kotlinStringType
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }

}
