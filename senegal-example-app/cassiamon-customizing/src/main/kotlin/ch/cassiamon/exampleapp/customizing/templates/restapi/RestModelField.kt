package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.exampleapp.customizing.templates.EntitySimpleAttributeConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class RestModelField(private val model: EntitySimpleAttributeConcept, private val restModelClass: RestModelClass, val kotlinModelField: KotlinModelField) {
    private val kotlinStringType = "kotlin.String"
    private val kotlinIntType = "kotlin.Int"
    private val kotlinBooleanType = "kotlin.Boolean"

    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: String = model.getType()

    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = when(entityAttributeType) {
        "Text" -> kotlinStringType
        "IntegerNumber" -> kotlinIntType
        "Boolean" -> kotlinBooleanType
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }

}
