package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.exampleapp.customizing.templates.EntityAttributeConcept
import ch.cassiamon.tools.CaseUtil

data class AngularModelField(private val model: EntityAttributeConcept, private val angularModelClass: AngularModelClass) {
    private val typescriptStringType = "string"
    private val typescriptIntType = "number"
    private val typescriptBooleanType = "boolean"

    private val entityAttributeName: String = model.name
    private val entityAttributeType: String = model.type

    val transferObjectFieldDefaultValue = when(entityAttributeType) {
        "Text" -> "''"
        "IntegerNumber" -> "0"
        "Boolean" -> "false"
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }
    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = when(entityAttributeType) {
        "Text" -> typescriptStringType
        "IntegerNumber" -> typescriptIntType
        "Boolean" -> typescriptBooleanType
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }

}
