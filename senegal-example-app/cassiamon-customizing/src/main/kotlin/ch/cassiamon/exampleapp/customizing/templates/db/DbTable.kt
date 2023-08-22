package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.*
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil
import java.lang.IllegalStateException

data class DbTable(private val entity: EntityConcept) {
    companion object {
        const val liquibaseIndexFileName = "structure.xml"
    }


    private val entityName: String = entity.getName()
    val humanReadableName: String = entityName
    val kotlinModelClass: KotlinModelClass = KotlinModelClass(entity)
    val tableName: String = CaseUtil.camelToSnakeCaseAllCaps(entityName)
    val liquibaseFileName: String = "${tableName}.structure.xml"
    val jooqDslName: String = "${entityName}Dsl"
    val jooqDslFileName: String = "${jooqDslName}.kt"
    val jooqDslPackage: String = "ch.senegal.example.persistence.entity.${entityName.lowercase()}"
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

    fun tableFields(): List<DbField> {
        return entity.entityFields().map { createDbField(it) }
    }

    fun referencingFields(): List<ForeignKeyDbField> {
        return tableFields()
            .filterIsInstance<ForeignKeyDbField>()
    }

    fun dataOnlyFields(): List<DataOnlyDbField> {
        return tableFields()
            .filterIsInstance<DataOnlyDbField>()
    }

    private fun createDbField(entityField: EntityField): DbField {
        val dbTable = DbTable(entity)
        val kotlinModelField = KotlinModelField(entityField, kotlinModelClass)
        return when(entityField) {
            is PrimaryKeyFieldConcept -> PrimaryKeyDbField(entityField, dbTable, kotlinModelField)
            is ReferenceToPrimaryKeyFieldConcept -> ForeignKeyDbField(entityField, dbTable, kotlinModelField)
            is DataOnlyFieldConcept -> DataOnlyDbField(entityField, dbTable, kotlinModelField)
        }
    }
}
