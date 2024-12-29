package org.codeblessing.senegal.codegen.templates.db

import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.kotlinTypeAsString
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.sqlTypeAsString
import org.codeblessing.senegal.codegen.schema.EntityField
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField
import org.codeblessing.senegal.codegen.toolbox.CaseUtil


sealed class DbField(entityField: EntityField, val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val isMandatoryField = true
    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = entityField.sqlTypeAsString()
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = entityField.kotlinTypeAsString()
}
