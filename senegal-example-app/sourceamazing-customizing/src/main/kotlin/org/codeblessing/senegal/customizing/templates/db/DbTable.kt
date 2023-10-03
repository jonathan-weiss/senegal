package org.codeblessing.senegal.customizing.templates.db

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
import org.codeblessing.sourceamazing.tools.CaseUtil

data class DbTable(private val entity: org.codeblessing.senegal.customizing.templates.EntityConcept) {
    companion object {
        const val liquibaseIndexFileName = "structure.xml"
    }


    private val entityName: String = entity.getName()
    val humanReadableName: String = entityName
    val kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass =
        org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass(entity)
    val tableName: String = CaseUtil.camelToSnakeCaseAllCaps(entityName)
    val liquibaseFileName: String = "${tableName}.structure.xml"
    val jooqDslName: String = "${entityName}Dsl"
    val jooqDslFileName: String = "${jooqDslName}.kt"
    val jooqDslPackage: String = "org.codeblessing.senegal.persistence.entity.${entityName.lowercase()}"
    val jooqRepositoryImplementationName: String = "Jooq${entityName}RepositoryImpl"
    val jooqRepositoryImplementationFileName: String = "${jooqRepositoryImplementationName}.kt"

    val primaryKeyColumnName: String
        get() = CaseUtil.camelToSnakeCaseAllCaps(entity.primaryKeyField().getName())
    val primaryKeyColumnType: String
        get() = entity.primaryKeyField().sqlTypeAsString()
    val primaryKeyJooqFieldName: String
        get() = CaseUtil.decapitalize(entity.primaryKeyField().getName())
    val primaryKeyJooqFieldType: String
        get() = "java.util.UUID"

    fun tableFields(): List<org.codeblessing.senegal.customizing.templates.db.DbField> {
        return entity.entityFields().map { createDbField(it) }
    }

    fun referencingFields(): List<org.codeblessing.senegal.customizing.templates.db.ForeignKeyDbField> {
        return tableFields()
            .filterIsInstance<org.codeblessing.senegal.customizing.templates.db.ForeignKeyDbField>()
    }

    fun dataOnlyFields(): List<org.codeblessing.senegal.customizing.templates.db.DataOnlyDbField> {
        return tableFields()
            .filterIsInstance<org.codeblessing.senegal.customizing.templates.db.DataOnlyDbField>()
    }

    private fun createDbField(entityField: org.codeblessing.senegal.customizing.templates.EntityField): org.codeblessing.senegal.customizing.templates.db.DbField {
        val dbTable = DbTable(entity)
        val kotlinModelField =
            org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField(entityField, kotlinModelClass)
        return when(entityField) {
            is org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept -> org.codeblessing.senegal.customizing.templates.db.PrimaryKeyDbField(
                entityField,
                dbTable,
                kotlinModelField
            )
            is org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept -> org.codeblessing.senegal.customizing.templates.db.ForeignKeyDbField(
                entityField,
                dbTable,
                kotlinModelField
            )
            is org.codeblessing.senegal.customizing.templates.DataOnlyFieldConcept -> org.codeblessing.senegal.customizing.templates.db.DataOnlyDbField(
                entityField,
                dbTable,
                kotlinModelField
            )
        }
    }
}
