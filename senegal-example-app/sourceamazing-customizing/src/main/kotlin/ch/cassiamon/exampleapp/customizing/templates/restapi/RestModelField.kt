package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.type
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import org.codeblessing.sourceamazing.tools.CaseUtil

data class RestModelField(private val model: EntityField, private val restModelClass: RestModelClass, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = model.type()

    val transferObjectFieldName = CaseUtil.decapitalize(entityAttributeName)
    val transferObjectFieldType = model.kotlinTypeAsString()

}
