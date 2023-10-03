package org.codeblessing.senegal.customizing.templates.db

class ForeignKeyDbField(private val entityField: org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept, dbTable: org.codeblessing.senegal.customizing.templates.db.DbTable, kotlinModelField: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField): org.codeblessing.senegal.customizing.templates.db.DbField(entityField, dbTable, kotlinModelField) {
    private val entityAttributeName: String = entityField.getName()

    val referencedDbTable: org.codeblessing.senegal.customizing.templates.db.DbTable
        get() = referencedDbTableOfPrimaryKeyField()
    val referencedDbField: org.codeblessing.senegal.customizing.templates.db.DbField
        get() = referencedPrimaryKeyField()
    val referenceName: String
        get() = "FK_${referencedDbTable.tableName}_${referencedDbField.columnName}"



    private fun asReferenceToPrimaryKeyFieldConcept(): org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept {
        return entityField
    }

    private fun referencedPrimaryKeyField(): org.codeblessing.senegal.customizing.templates.db.PrimaryKeyDbField {
        val primaryKeyFieldConcept = asReferenceToPrimaryKeyFieldConcept().getReferencedPrimaryKeyField()
        val entityConcept = primaryKeyFieldConcept.getParentEntity()
        return org.codeblessing.senegal.customizing.templates.db.PrimaryKeyDbField(
            primaryKeyFieldConcept,
            org.codeblessing.senegal.customizing.templates.db.DbTable(entityConcept),
            org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField(
                primaryKeyFieldConcept,
                org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass(entityConcept)
            )
        )
    }

    private fun referencedDbTableOfPrimaryKeyField(): org.codeblessing.senegal.customizing.templates.db.DbTable {
        return referencedPrimaryKeyField().dbTable
    }

}
