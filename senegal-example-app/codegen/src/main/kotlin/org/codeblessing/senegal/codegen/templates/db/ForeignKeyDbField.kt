package org.codeblessing.senegal.codegen.templates.db

import org.codeblessing.senegal.codegen.schema.ReferenceToPrimaryKeyFieldConcept
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelClass
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField

class ForeignKeyDbField(private val entityField: ReferenceToPrimaryKeyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(entityField, dbTable, kotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val referencedDbTable: DbTable
        get() = referencedDbTableOfPrimaryKeyField()
    val referencedDbField: DbField
        get() = referencedPrimaryKeyField()
    val referenceName: String
        get() = "FK_${referencedDbTable.tableName}_${referencedDbField.columnName}"



    private fun asReferenceToPrimaryKeyFieldConcept(): ReferenceToPrimaryKeyFieldConcept {
        return entityField
    }

    private fun referencedPrimaryKeyField(): PrimaryKeyDbField {
        val primaryKeyFieldConcept = asReferenceToPrimaryKeyFieldConcept().getReferencedPrimaryKeyField()
        val entityConcept = primaryKeyFieldConcept.getParentEntity()
        return PrimaryKeyDbField(
            primaryKeyFieldConcept,
            DbTable(entityConcept),
            KotlinModelField(
                primaryKeyFieldConcept,
                KotlinModelClass(entityConcept)
            )
        )
    }

    private fun referencedDbTableOfPrimaryKeyField(): DbTable {
        return referencedPrimaryKeyField().dbTable
    }

}
