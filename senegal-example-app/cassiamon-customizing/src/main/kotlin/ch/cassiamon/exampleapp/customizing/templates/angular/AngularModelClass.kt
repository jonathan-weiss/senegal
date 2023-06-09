package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class AngularModelClass(private val model: EntityConcept) {

    val kotlinModelClass: KotlinModelClass = KotlinModelClass(model)
    val entityName: String = model.name

    val entityFileName: String = CaseUtil.camelToDashCase(this.entityName)
    val decapitalizedEntityName: String = CaseUtil.decapitalize(this.entityName)
    val transferObjectIdFieldName: String = "${CaseUtil.decapitalize(this.entityName)}Id"
    val transferObjectIdFieldType: String = "UuidTO"
    val restApiUrlPrefixName: String = CaseUtil.camelToDashCase(this.entityName)

    fun angularFields(): List<AngularModelField> {
        return model.entityAttributes().map { AngularModelField(it, this) }
    }
}
