package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinIdClass
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import ch.cassiamon.tools.CaseUtil

data class KotlinModelClass(private val model: EntityConcept) {
    private val entityName: String = model.getName()
    val kotlinClassName: String = entityName
    val kotlinFileName: String = "${kotlinClassName}.kt"
    val kotlinPackage: String = "ch.senegal.example.domain.entity.${entityName.lowercase()}"
    val kotlinRepositoryName: String = "${kotlinClassName}Repository"
    val idFieldName
        get() = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val idFieldType
        get() = model.kotlinIdClass()

    fun kotlinFields(): List<KotlinModelField> {
        return model.entityFields().map { KotlinModelField(it, this) }
    }
}
