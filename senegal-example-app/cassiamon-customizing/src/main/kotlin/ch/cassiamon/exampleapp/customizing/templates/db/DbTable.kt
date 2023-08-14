package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class DbTable(private val model: EntityConcept) {
    companion object {
        const val liquibaseIndexFileName = "structure.xml"
    }

    private val entityName: String = model.getName()
    val humanReadableName: String = entityName
    val kotlinModelClass: KotlinModelClass = KotlinModelClass(model)
    val tableName: String = CaseUtil.camelToSnakeCaseAllCaps(entityName)
    val liquibaseFileName: String = "${tableName}.structure.xml"
    val jooqDslName: String = "${entityName}Dsl"
    val jooqDslFileName: String = "${jooqDslName}.kt"
    val jooqDslPackage: String = "ch.senegal.example.persistence.entity.${entityName.lowercase()}"
    val jooqRepositoryImplementationName: String = "Jooq${entityName}RepositoryImpl"
    val jooqRepositoryImplementationFileName: String = "${jooqRepositoryImplementationName}.kt"
    val primaryKeyColumnName = CaseUtil.camelToSnakeCaseAllCaps("${entityName}Id")
    val primaryKeyColumnType = "UUID"
    val primaryKeyJooqFieldName = CaseUtil.decapitalize("${entityName}Id")
    val primaryKeyJooqFieldType = "UUID"

    fun tableFields(): List<DbField> {
        return model.entityAttributes().map { DbField(it, this, KotlinModelField(it, kotlinModelClass)) }
    }
}
