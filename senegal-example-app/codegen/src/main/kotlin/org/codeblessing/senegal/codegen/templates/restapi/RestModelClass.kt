package org.codeblessing.senegal.codegen.templates.restapi

import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.senegal.codegen.schema.EntityConcept
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelClass
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField
import org.codeblessing.senegal.codegen.toolbox.CaseUtil


data class RestModelClass(private val model: EntityConcept) {

    private val entityName: String = model.getName()
    val kotlinModelClass: KotlinModelClass =
        KotlinModelClass(model)
    val facadeClassName: String = "${entityName}Facade"
    val facadePackageName: String = "org.codeblessing.senegal.frontendapi.facade.${CaseUtil.decapitalize(entityName)}"
    val controllerClassName: String = "${entityName}Controller"
    val controllerPackageName: String = "org.codeblessing.senegal.frontendapi.controller.${CaseUtil.decapitalize(entityName)}"
    val transferObjectBaseName: String = entityName
    val transferObjectPackageName: String = facadePackageName
    val transferObjectIdFieldName: String = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val urlPrefix: String = CaseUtil.camelToDashCase(entityName)

    fun fields(): List<RestModelField> {
        return model.entityFields().map {
            RestModelField(
                it,
                this,
                KotlinModelField(it, kotlinModelClass)
            )
        }
    }
}
