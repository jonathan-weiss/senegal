package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.tools.CaseUtil

data class DbTable(private val model: EntityConcept) {
    companion object {
        const val liquibaseIndexFileName = "structure.xml"
    }

    private val entityName: String = model.name
    val tableName: String = CaseUtil.camelToSnakeCaseAllCaps(entityName)
    val liquibaseFileName: String = "${tableName}.structure.xml"
    val jpaEntityName: String = "${entityName}JpaEntity"
    val jpaEntityPackage: String = "ch.example.app.my.${entityName.lowercase()}" // TODO
    val primaryKeyColumnName = CaseUtil.camelToSnakeCaseAllCaps("${entityName}Id")
    val primaryKeyColumnType = "UUID"
    val primaryKeyJpaFieldName = CaseUtil.decapitalize("${entityName}Id")
    val primaryKeyJpaFieldType = "UUID"

    fun tableFields(): List<DbField> {
        return model.entityAttributes().map { DbField(it, this) }
    }
}
