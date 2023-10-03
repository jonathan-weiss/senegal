package org.codeblessing.senegal.customizing.templates.angular

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.type
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.typescriptTypeAsString
import org.codeblessing.sourceamazing.tools.CaseUtil

data class AngularModelField(private val model: org.codeblessing.senegal.customizing.templates.EntityField, private val angularModelClass: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: org.codeblessing.senegal.customizing.templates.FieldDataType = model.type()

    val transferObjectFieldDefaultValue = when(entityAttributeType) {
        org.codeblessing.senegal.customizing.templates.FieldDataType.TEXT -> "''"
        org.codeblessing.senegal.customizing.templates.FieldDataType.UUID -> "''"
        else -> throw RuntimeException("Unknown Type for $entityAttributeType")
    }
    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = model.typescriptTypeAsString()

    val fieldName = CaseUtil.capitalize(entityAttributeName)
    val fieldFileName = CaseUtil.decapitalize(entityAttributeName)
    val decapitalizedFieldName = CaseUtil.decapitalize(entityAttributeName)

}
