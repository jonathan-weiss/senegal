package ${templateModel.SqlDbJpaEntityPackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "${templateModel.SqlDbTableName}")
class ${templateModel.SqlDbJpaEntityName}(
    @Id
    @Column(name = "${templateModel.SqlDbPrimaryKeyColumnName}")
    val ${templateModel.SqlDbPrimaryKeyJpaFieldName}: ${templateModel.SqlDbPrimaryKeyJpaFieldType},<#list templateModel.childNodes as fieldNode>

    @Column(name = "${fieldNode.SqlDbColumnName}")
    val ${fieldNode.SqlDbJpaFieldName}: ${fieldNode.SqlDbJpaFieldType},</#list>
) {
    companion object {
        fun fromDomain(domainInstance: ${templateModel.KotlinModelClassName}) = ${templateModel.SqlDbJpaEntityName}(
            ${templateModel.SqlDbPrimaryKeyJpaFieldName} = domainInstance.${templateModel.KotlinModelIdFieldName}.value,<#list templateModel.childNodes as fieldNode>
            ${fieldNode.SqlDbJpaFieldName} = domainInstance.${fieldNode.KotlinModelFieldName},</#list>
        )
    }

    fun toDomain() = ${templateModel.KotlinModelClassName}(
        ${templateModel.KotlinModelIdFieldName} = ${templateModel.KotlinModelIdFieldType}(this.${templateModel.SqlDbPrimaryKeyJpaFieldName}),<#list templateModel.childNodes as fieldNode>
        ${fieldNode.KotlinModelFieldName} = this.${fieldNode.SqlDbJpaFieldName},</#list>
    )

}
