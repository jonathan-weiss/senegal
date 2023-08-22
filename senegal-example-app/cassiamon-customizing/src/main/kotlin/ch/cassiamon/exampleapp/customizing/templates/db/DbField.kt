package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.PrimaryKeyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

sealed class DbField(entityField: EntityField, val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val isMandatoryField = true
    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = entityField.sqlTypeAsString()
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = entityField.kotlinTypeAsString()
}
