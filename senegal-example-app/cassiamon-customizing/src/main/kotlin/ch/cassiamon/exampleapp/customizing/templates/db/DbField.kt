package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class DbField(private val model: EntityField, private val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = model.getName()

    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = EntityFieldHelper.sqlTypeAsString(model)
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = EntityFieldHelper.kotlinTypeAsString(model)
}
