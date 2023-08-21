package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinIdClass
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinTypeAsString
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.sqlTypeAsString
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

    val primaryKeyColumnName: String
        get() = CaseUtil.camelToSnakeCaseAllCaps(model.primaryKeyField().getName())
    val primaryKeyColumnType: String
        get() = model.primaryKeyField().sqlTypeAsString()
    val primaryKeyJooqFieldName: String
        get() = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val primaryKeyJooqFieldType: String
        get() = "java.util.UUID"

    fun tableFields(): List<DbField> {
        return model.entityFields().map { DbField(it, this, KotlinModelField(it, kotlinModelClass)) }
    }

    fun referencingFields(): List<DbField> {
        return model.entityReferences().map { DbField(it, this, KotlinModelField(it, kotlinModelClass)) }
    }

}
