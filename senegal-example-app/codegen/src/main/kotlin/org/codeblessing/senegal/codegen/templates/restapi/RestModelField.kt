package org.codeblessing.senegal.codegen.templates.restapi

import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.type
import org.codeblessing.senegal.codegen.schema.EntityField
import org.codeblessing.senegal.codegen.schema.FieldDataType
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField
import org.codeblessing.senegal.codegen.toolbox.CaseUtil


data class RestModelField(private val model: EntityField, private val restModelClass: RestModelClass, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = model.type()

    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = model.kotlinTypeAsString()

}
