package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object JooqRepositoryImplTemplate {

    fun fillTemplate(dbTable: DbTable): String {
        return """
            package ${dbTable.jooqDslPackage}
            
           
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinClassName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinRepositoryName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.idFieldType}
            import org.jooq.DSLContext
            import org.jooq.Record
            import org.springframework.stereotype.Repository
            
            
            @Repository
            class ${dbTable.jooqRepositoryImplementationName}(
                    private val jooqDsl: DSLContext,
            ) : ${dbTable.kotlinModelClass.kotlinRepositoryName} {
            
                override fun fetch${dbTable.kotlinModelClass.kotlinClassName}ById(${dbTable.kotlinModelClass.idFieldName}: ${dbTable.kotlinModelClass.idFieldType}): ${dbTable.kotlinModelClass.kotlinClassName} {
                    return jooqDsl
                            .selectFrom(${dbTable.jooqDslName}.TABLE)
                            .where(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}.eq(${dbTable.kotlinModelClass.idFieldName}.value))
                            .fetchOne(this::toDomain)
                            ?: throw RuntimeException("${dbTable.humanReadableName} with id ${'$'}${dbTable.kotlinModelClass.idFieldName} not found")
                }
            
                override fun fetchAll${dbTable.kotlinModelClass.kotlinClassName}(): List<${dbTable.kotlinModelClass.kotlinClassName}> {
                    return jooqDsl
                            .selectFrom(${dbTable.jooqDslName}.TABLE)
                            .fetch(this::toDomain)
                }
                
                override fun fetchAll${dbTable.kotlinModelClass.kotlinClassName}Filtered(searchTerm: String): List<${dbTable.kotlinModelClass.kotlinClassName}> {
                    return jooqDsl
                        .selectFrom(${dbTable.jooqDslName}.TABLE)
                        .where(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}.like("%${'$'}searchTerm%"))
                        ${StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """.or( ${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}.like("%${'$'}searchTerm%"))
                        """}}
                        .fetch(this::toDomain)
                }
            
                override fun insert${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jooqDsl.insertInto(${dbTable.jooqDslName}.TABLE)
                        .set(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}, domainInstance.${dbTable.kotlinModelClass.idFieldName}.value)
                        ${StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """.set(${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}, domainInstance.${dbField.kotlinModelField.kotlinFieldName})
                        """}}
                        .execute()
                }
            
                override fun update${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jooqDsl.update(${dbTable.jooqDslName}.TABLE)
                        ${StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """.set(${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}, domainInstance.${dbField.kotlinModelField.kotlinFieldName})
                        """}}
                        .where(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}.eq(domainInstance.${dbTable.kotlinModelClass.idFieldName}.value))
                        .execute()
                }
            
                override fun delete${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jooqDsl.deleteFrom(${dbTable.jooqDslName}.TABLE)
                        .where(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}.eq(domainInstance.${dbTable.kotlinModelClass.idFieldName}.value))
                        .execute()
                }
                
                private fun toDomain(record: Record): ${dbTable.kotlinModelClass.kotlinClassName} {
                    return ${dbTable.kotlinModelClass.kotlinClassName}(
                        ${dbTable.kotlinModelClass.idFieldName} = ${dbTable.kotlinModelClass.idFieldType}(record.get(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName})),
                        ${StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """${dbField.kotlinModelField.kotlinFieldName} = record.get(${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}),
                        """}}
                    )
                }
            }
        """.identForMarker()
    }
}
