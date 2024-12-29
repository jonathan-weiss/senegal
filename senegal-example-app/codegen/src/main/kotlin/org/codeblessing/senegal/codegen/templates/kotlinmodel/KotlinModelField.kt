package org.codeblessing.senegal.codegen.templates.kotlinmodel

import helper.EntityFieldHelper.kotlinTypeAsString
import helper.EntityFieldHelper.type
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.type
import org.codeblessing.senegal.codegen.schema.EntityField
import org.codeblessing.senegal.codegen.schema.FieldDataType
import org.codeblessing.senegal.codegen.toolbox.CaseUtil


data class KotlinModelField(private val model: EntityField, val kotlinModelClass: KotlinModelClass) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = model.type()

    val kotlinFieldName = CaseUtil.decapitalize(entityAttributeName)
    val kotlinFieldType = model.kotlinTypeAsString()

}
