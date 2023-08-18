package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.tools.CaseUtil

data class AngularModelField(private val model: EntityField, private val angularModelClass: AngularModelClass) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = EntityFieldHelper.type(model)

    val transferObjectFieldDefaultValue = when(entityAttributeType) {
        FieldDataType.TEXT -> "''"
        FieldDataType.UUID -> "''"
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }
    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = EntityFieldHelper.typescriptTypeAsString(model)

    val fieldName = CaseUtil.capitalize(entityAttributeName)
    val fieldFileName = CaseUtil.decapitalize(entityAttributeName)
    val decapitalizedFieldName = CaseUtil.decapitalize(entityAttributeName)

}
