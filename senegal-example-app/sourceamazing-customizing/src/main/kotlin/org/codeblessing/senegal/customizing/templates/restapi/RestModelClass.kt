package org.codeblessing.senegal.customizing.templates.restapi

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.sourceamazing.tools.CaseUtil

data class RestModelClass(private val model: org.codeblessing.senegal.customizing.templates.EntityConcept) {

    private val entityName: String = model.getName()
    val kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass =
        org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass(model)
    val facadeClassName: String = "${entityName}Facade"
    val facadePackageName: String = "org.codeblessing.senegal.frontendapi.facade.${CaseUtil.decapitalize(entityName)}"
    val controllerClassName: String = "${entityName}Controller"
    val controllerPackageName: String = "org.codeblessing.senegal.frontendapi.controller.${CaseUtil.decapitalize(entityName)}"
    val transferObjectBaseName: String = entityName
    val transferObjectPackageName: String = facadePackageName
    val transferObjectIdFieldName: String = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val urlPrefix: String = CaseUtil.camelToDashCase(entityName)

    fun fields(): List<org.codeblessing.senegal.customizing.templates.restapi.RestModelField> {
        return model.entityFields().map {
            org.codeblessing.senegal.customizing.templates.restapi.RestModelField(
                it,
                this,
                org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField(it, kotlinModelClass)
            )
        }
    }
}
