package org.codeblessing.senegal.customizing.templates.db

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
import org.codeblessing.sourceamazing.tools.CaseUtil

sealed class DbField(entityField: org.codeblessing.senegal.customizing.templates.EntityField, val dbTable: org.codeblessing.senegal.customizing.templates.db.DbTable, val kotlinModelField: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val isMandatoryField = true
    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = entityField.sqlTypeAsString()
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = entityField.kotlinTypeAsString()
}
