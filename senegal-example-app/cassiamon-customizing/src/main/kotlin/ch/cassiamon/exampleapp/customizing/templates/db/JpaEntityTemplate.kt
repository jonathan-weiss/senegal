package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object JpaEntityTemplate {

    fun fillTemplate(dbTable: DbTable): String {
        return """
            package ${dbTable.jpaEntityPackage}
            
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinClassName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.idFieldType}
            import java.util.*
            import javax.persistence.Column
            import javax.persistence.Entity
            import javax.persistence.Id
            import javax.persistence.Table
            
            
            @Entity
            @Table(name = "${dbTable.tableName}")
            class ${dbTable.jpaEntityName}(
                @Id
                @Column(name = "${dbTable.primaryKeyColumnName}")
                val ${dbTable.primaryKeyJpaFieldName}: ${dbTable.primaryKeyJpaFieldType},
                
                ${forEach(dbTable.tableFields()) { dbField ->
            """
                @Column(name = "${dbField.columnName}")
                val ${dbField.jpaFieldName}: ${dbField.jpaFieldType},
            """ } }
            ) {
                companion object {
                    fun fromDomain(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) = ${dbTable.jpaEntityName}(
                        ${dbTable.primaryKeyJpaFieldName} = domainInstance.${dbTable.kotlinModelClass.idFieldName}.value,${forEach(dbTable.tableFields()) { dbField ->
                        """
                        ${dbField.jpaFieldName} = domainInstance.${dbField.kotlinModelField.kotlinFieldName},
                        """ } }
                    )
                }
            
                fun toDomain() = ${dbTable.kotlinModelClass.kotlinClassName}(
                    ${dbTable.kotlinModelClass.idFieldName} = ${dbTable.kotlinModelClass.idFieldType}(this.${dbTable.primaryKeyJpaFieldName}),${forEach(dbTable.tableFields()) { dbField ->
                    """
                    ${dbField.kotlinModelField.kotlinFieldName} = this.${dbField.jpaFieldName},
                    """ } }
                )
            
            }
        """.identForMarker()
    }
}
