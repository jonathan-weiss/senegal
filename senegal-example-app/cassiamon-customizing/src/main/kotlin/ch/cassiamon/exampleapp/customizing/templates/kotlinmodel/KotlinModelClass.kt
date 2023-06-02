package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.tools.CaseUtil

data class KotlinModelClass(private val model: EntityConcept) {
    private val entityName: String = model.name
    val kotlinClassName: String = entityName
    val kotlinFileName: String = "${kotlinClassName}.kt"
    val kotlinPackage: String = "ch.senegal.example.domain.entity.${entityName.lowercase()}"
    val kotlinRepositoryName: String = "${kotlinClassName}Repository"
    val idFieldName = "${CaseUtil.decapitalize(kotlinClassName)}Id"
    val idFieldType = "${kotlinClassName}Id"

    fun kotlinFields(): List<KotlinModelField> {
        return model.entityAttributes().map { KotlinModelField(it, this) }
    }
}
