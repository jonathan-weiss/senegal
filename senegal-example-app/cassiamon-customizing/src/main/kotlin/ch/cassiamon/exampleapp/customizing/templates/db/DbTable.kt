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
    val kotlinModelClass: KotlinModelClass = KotlinModelClass(model)
    val tableName: String = CaseUtil.camelToSnakeCaseAllCaps(entityName)
    val liquibaseFileName: String = "${tableName}.structure.xml"
    val jpaEntityName: String = "${entityName}JpaEntity"
    val jpaEntityFileName: String = "${jpaEntityName}.kt"
    val jpaEntityPackage: String = "ch.senegal.example.persistence.entity.${entityName.lowercase()}"
    val jpaRepositoryName: String = "${entityName}JpaRepository"
    val jpaRepositoryFileName: String = "${jpaRepositoryName}.kt"
    val jpaRepositoryPackage: String = jpaEntityPackage
    val repositoryImplementationName: String = "${entityName}RepositoryImpl"
    val repositoryImplementationFileName: String = "${repositoryImplementationName}.kt"
    val repositoryImplementationPackage: String = jpaEntityPackage
    val primaryKeyColumnName = CaseUtil.camelToSnakeCaseAllCaps("${entityName}Id")
    val primaryKeyColumnType = "UUID"
    val primaryKeyJpaFieldName = CaseUtil.decapitalize("${entityName}Id")
    val primaryKeyJpaFieldType = "UUID"

    fun tableFields(): List<DbField> {
        return model.entityAttributes().map { DbField(it, this, KotlinModelField(it, kotlinModelClass)) }
    }
}
