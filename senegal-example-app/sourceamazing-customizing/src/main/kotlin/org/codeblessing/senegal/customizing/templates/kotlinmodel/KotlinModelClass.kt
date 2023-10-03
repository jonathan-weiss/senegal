package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.kotlinIdClass
import org.codeblessing.senegal.customizing.templates.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.sourceamazing.tools.CaseUtil

data class KotlinModelClass(private val model: org.codeblessing.senegal.customizing.templates.EntityConcept) {
    private val entityName: String = model.getName()
    val kotlinClassName: String = entityName
    val kotlinFileName: String = "${kotlinClassName}.kt"
    val kotlinPackage: String = "org.codeblessing.senegal.domain.entity.${entityName.lowercase()}"
    val kotlinRepositoryName: String = "${kotlinClassName}Repository"
    val idFieldName
        get() = CaseUtil.decapitalize(model.primaryKeyField().getName())
    val idFieldType
        get() = model.kotlinIdClass()
    val kotlinClassNameAsFieldName: String = CaseUtil.decapitalize(entityName)


    fun kotlinFields(): List<org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField> {
        return model.entityFields().map {
            org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField(
                it,
                this
            )
        }
    }

    fun referencingFields(): List<org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField> {
        return model.entityReferences()
            .map { referenceToPrimaryKeyFieldConcept -> referenceToPrimaryKeyFieldConcept.getReferencedPrimaryKeyField() }
            .map {
                org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelField(
                    it,
                    KotlinModelClass(it.getParentEntity())
                )
            }
    }
}
