package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.FieldDataType
import ch.cassiamon.exampleapp.customizing.templates.PrimaryKeyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.ReferenceToPrimaryKeyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class DbField(private val model: EntityField, private val dbTable: DbTable, val kotlinModelField: KotlinModelField) {
    private val entityAttributeName: String = model.getName()

    val isPrimaryKey = model is PrimaryKeyFieldConcept
    val isMandatoryField = true
    val columnName = CaseUtil.camelToSnakeCaseAllCaps(entityAttributeName)
    val columnType = model.sqlTypeAsString()
    val jooqFieldName = CaseUtil.decapitalize(entityAttributeName)
    val jooqFieldType = model.kotlinTypeAsString()

    val isReferenceField = false // TODO
    val referencedDbTable: DbTable = dbTable // TODO
    val referencedDbField: DbField = this // TODO
    val referenceName: String = "FK_${referencedDbTable.tableName}_${referencedDbField.columnName}"

    private fun referencedTableField() {
        if(model is ReferenceToPrimaryKeyFieldConcept) {
            model.getReferencedPrimaryKeyField()
        } else {

        }
    }
}
