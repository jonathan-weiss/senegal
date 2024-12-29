package org.codeblessing.senegal.codegen.templates.kotlinmodel

import helper.EntityFieldHelper.kotlinIdClass
import helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.kotlinIdClass
import org.codeblessing.senegal.codegen.helper.EntityFieldHelper.primaryKeyField
import org.codeblessing.senegal.codegen.schema.EntityConcept
import org.codeblessing.senegal.codegen.toolbox.CaseUtil


data class KotlinModelClass(private val model: EntityConcept) {
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


    fun kotlinFields(): List<KotlinModelField> {
        return model.entityFields().map {
            KotlinModelField(
                it,
                this
            )
        }
    }

    fun referencingFields(): List<KotlinModelField> {
        return model.entityReferences()
            .map { referenceToPrimaryKeyFieldConcept -> referenceToPrimaryKeyFieldConcept.getReferencedPrimaryKeyField() }
            .map {
                KotlinModelField(
                    it,
                    KotlinModelClass(it.getParentEntity())
                )
            }
    }
}
