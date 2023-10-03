package org.codeblessing.senegal.customizing.templates.restapi

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.type
import org.codeblessing.sourceamazing.tools.CaseUtil

data class RestModelField(private val model: org.codeblessing.senegal.customizing.templates.EntityField, private val restModelClass: org.codeblessing.senegal.customizing.templates.restapi.RestModelClass, val kotlinModelField: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: org.codeblessing.senegal.customizing.templates.FieldDataType = model.type()

    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = model.kotlinTypeAsString()

}
