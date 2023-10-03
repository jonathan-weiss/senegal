package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.type
import org.codeblessing.sourceamazing.tools.CaseUtil

data class KotlinModelField(private val model: org.codeblessing.senegal.customizing.templates.EntityField, val kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: org.codeblessing.senegal.customizing.templates.FieldDataType = model.type()

    val kotlinFieldName = CaseUtil.decapitalize(entityAttributeName)
    val kotlinFieldType = model.kotlinTypeAsString()

}
