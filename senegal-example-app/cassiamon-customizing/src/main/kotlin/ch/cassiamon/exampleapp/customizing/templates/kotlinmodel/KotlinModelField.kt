package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.tools.CaseUtil

data class KotlinModelField(private val model: EntityField, private val kotlinModelClass: KotlinModelClass) {
    private val entityAttributeName: String = model.getName()
    private val entityAttributeType: FieldDataType = EntityFieldHelper.type(model)

    val kotlinFieldName = CaseUtil.decapitalize(entityAttributeName)
    val kotlinFieldType = EntityFieldHelper.kotlinTypeAsString(model)

}
