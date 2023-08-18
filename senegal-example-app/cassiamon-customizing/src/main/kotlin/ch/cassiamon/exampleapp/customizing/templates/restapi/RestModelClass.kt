package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.kotlinIdClass
import ch.cassiamon.exampleapp.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelClass
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField
import ch.cassiamon.tools.CaseUtil

data class RestModelClass(private val model: EntityConcept) {

    private val entityName: String = model.getName()
    val kotlinModelClass: KotlinModelClass = KotlinModelClass(model)
    val facadeClassName: String = "${entityName}Facade"
    val facadePackageName: String = "ch.senegal.example.frontendapi.facade.${CaseUtil.decapitalize(entityName)}"
    val controllerClassName: String = "${entityName}Controller"
    val controllerPackageName: String = "ch.senegal.example.frontendapi.controller.${CaseUtil.decapitalize(entityName)}"
    val transferObjectBaseName: String = entityName
    val transferObjectPackageName: String = facadePackageName
    val transferObjectIdFieldName: String = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val urlPrefix: String = CaseUtil.camelToDashCase(entityName)

    fun fields(): List<RestModelField> {
        return model.entityFields().map { RestModelField(it, this, KotlinModelField(it, kotlinModelClass)) }
    }
}
