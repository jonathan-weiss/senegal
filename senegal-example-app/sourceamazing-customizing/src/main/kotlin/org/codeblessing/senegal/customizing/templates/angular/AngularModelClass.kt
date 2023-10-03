package org.codeblessing.senegal.customizing.templates.angular

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.sourceamazing.tools.CaseUtil

data class AngularModelClass(private val model: org.codeblessing.senegal.customizing.templates.EntityConcept) {

    val kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass =
        org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass(model)
    val entityName: String = model.getName()

    val entityFileName: String = CaseUtil.camelToDashCase(this.entityName)
    val decapitalizedEntityName: String = CaseUtil.decapitalize(this.entityName)
    val transferObjectIdFieldName: String = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val transferObjectIdFieldFileName: String = CaseUtil.camelToDashCase(model.primaryKeyField().getName())
    val restApiUrlPrefixName: String = CaseUtil.camelToDashCase(this.entityName)

    fun angularFields(): List<org.codeblessing.senegal.customizing.templates.angular.AngularModelField> {
        return model.entityFields().map {
            org.codeblessing.senegal.customizing.templates.angular.AngularModelField(
                it,
                this
            )
        }
    }
}
