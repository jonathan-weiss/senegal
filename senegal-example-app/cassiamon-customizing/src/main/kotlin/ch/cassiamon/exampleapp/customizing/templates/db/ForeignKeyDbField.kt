package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.ReferenceToPrimaryKeyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField

class ForeignKeyDbField(private val entityField: ReferenceToPrimaryKeyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(entityField, dbTable, kotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val referencedDbTable: DbTable
        get() = referencedDbTableOfPrimaryKeyField()
    val referencedDbField: DbField
        get() = referencedPrimaryKeyField()
    val referenceName: String
        get() = "FK_${referencedDbTable.tableName}_${referencedDbField.columnName}"



    private fun asReferenceToPrimaryKeyFieldConcept():ReferenceToPrimaryKeyFieldConcept {
        return entityField
    }

    private fun referencedPrimaryKeyField(): PrimaryKeyDbField {
        val primaryKeyFieldConcept = asReferenceToPrimaryKeyFieldConcept().getReferencedPrimaryKeyField()
        val entityConcept = primaryKeyFieldConcept.getParentEntity()
        return PrimaryKeyDbField(primaryKeyFieldConcept, DbTable(entityConcept), KotlinModelField(primaryKeyFieldConcept, KotlinModelClass(entityConcept)))
    }

    private fun referencedDbTableOfPrimaryKeyField(): DbTable {
        return referencedPrimaryKeyField().dbTable
    }

}
