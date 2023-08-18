package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.tools.CaseUtil

data class AngularModelClass(private val model: EntityConcept) {

    val kotlinModelClass: KotlinModelClass = KotlinModelClass(model)
    val entityName: String = model.getName()

    val entityFileName: String = CaseUtil.camelToDashCase(this.entityName)
    val decapitalizedEntityName: String = CaseUtil.decapitalize(this.entityName)
    val transferObjectIdFieldName: String = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val transferObjectIdFieldFileName: String = CaseUtil.camelToDashCase(model.primaryKeyField().getName())
    val restApiUrlPrefixName: String = CaseUtil.camelToDashCase(this.entityName)

    fun angularFields(): List<AngularModelField> {
        return model.entityFields().map { AngularModelField(it, this) }
    }
}
