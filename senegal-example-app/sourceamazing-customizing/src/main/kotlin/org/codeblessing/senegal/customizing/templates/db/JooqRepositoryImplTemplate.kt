package org.codeblessing.senegal.customizing.templates.db

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object JooqRepositoryImplTemplate {

    fun fillTemplate(dbTable: org.codeblessing.senegal.customizing.templates.db.DbTable): String {
        return """
            package ${dbTable.jooqDslPackage}
            
           
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinClassName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinRepositoryName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.idFieldType}
            import org.jooq.DSLContext
            import org.jooq.Record
            import org.springframework.stereotype.Repository
           ${
            StringTemplateHelper.forEach(dbTable.referencingFields()) { dbReferenceField ->
            """
            import ${dbReferenceField.referencedDbTable.kotlinModelClass.kotlinPackage}.${dbReferenceField.referencedDbTable.kotlinModelClass.idFieldType}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinClassName}${dbReferenceField.referencedDbTable.kotlinModelClass.kotlinClassName}Summary
            import ${dbReferenceField.referencedDbTable.jooqDslPackage}.${dbReferenceField.referencedDbTable.jooqDslName}
            """}}

            
            
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
                        ${
            StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """
                            .or( ${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}.like("%${'$'}searchTerm%"))""".trimIndent()}}
                        .fetch(this::toDomain)
                }

                

           ${
            StringTemplateHelper.forEach(dbTable.referencingFields()) { dbReferenceField ->
               val referencedKotlinModelClass = dbReferenceField.referencedDbTable.kotlinModelClass
               val referencedDbTable = dbReferenceField.referencedDbTable
               
            """
                override fun fetchAll${dbTable.kotlinModelClass.kotlinClassName}By${referencedKotlinModelClass.kotlinClassName}(${referencedKotlinModelClass.idFieldName}: ${referencedKotlinModelClass.idFieldType}): List<${dbTable.kotlinModelClass.kotlinClassName}> {
                    return jooqDsl
                        .selectFrom(${dbTable.jooqDslName}.TABLE)
                        .where(${dbTable.jooqDslName}.TABLE.${dbReferenceField.jooqFieldName}.eq(${referencedKotlinModelClass.idFieldName}.value))
                        .fetch(this::toDomain)
                }
                
                override fun fetch${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}SummaryById(${referencedKotlinModelClass.idFieldName}: ${referencedKotlinModelClass.idFieldType}): ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary {
                    return fetch${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary(${referencedKotlinModelClass.idFieldName})
                }
                
                private fun fetch${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary(${referencedKotlinModelClass.idFieldName}: ${referencedKotlinModelClass.kotlinClassName}Id): ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary {
                    return pick${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary(${referencedKotlinModelClass.idFieldName}, fetch${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summaries(listOf(${referencedKotlinModelClass.idFieldName})))
                }
            
            
                private fun pick${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary(${referencedKotlinModelClass.idFieldName}: ${referencedKotlinModelClass.kotlinClassName}Id, summaries: Map<${referencedKotlinModelClass.kotlinClassName}Id, ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary>): ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary {
                    return summaries[${referencedKotlinModelClass.idFieldName}] ?: throw IllegalStateException("${referencedKotlinModelClass.kotlinClassName} not found for id ${'$'}${referencedKotlinModelClass.idFieldName}")
                }
            
                private fun fetch${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summaries(${referencedKotlinModelClass.idFieldName}List: List<${referencedKotlinModelClass.kotlinClassName}Id>): Map<${referencedKotlinModelClass.kotlinClassName}Id, ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary> {
                    val records = jooqDsl
                        .selectFrom(${referencedDbTable.jooqDslName}.TABLE)
                        .where(${referencedDbTable.jooqDslName}.TABLE.${referencedDbTable.primaryKeyJooqFieldName}.`in`(${referencedKotlinModelClass.idFieldName}List.map { ${referencedKotlinModelClass.kotlinClassNameAsFieldName}Id -> ${referencedKotlinModelClass.kotlinClassNameAsFieldName}Id.value }))
                        .fetch()
            
                    return records.map { record -> ${dbTable.kotlinModelClass.kotlinClassName}${referencedKotlinModelClass.kotlinClassName}Summary(
                        ${referencedKotlinModelClass.idFieldName} = ${referencedKotlinModelClass.kotlinClassName}Id(record.get(${referencedDbTable.jooqDslName}.TABLE.${referencedDbTable.primaryKeyJooqFieldName})),
                        ${
                StringTemplateHelper.forEach(referencedDbTable.tableFields()) { tableField -> """
                            ${tableField.kotlinModelField.kotlinFieldName} = record.get(${referencedDbTable.jooqDslName}.TABLE.${tableField.jooqFieldName}),    
                        """.trimIndent()} }
                    ) }.associateBy { ${referencedKotlinModelClass.kotlinClassNameAsFieldName} -> ${referencedKotlinModelClass.kotlinClassNameAsFieldName}.${referencedKotlinModelClass.idFieldName} }
                }


            """}}

                override fun insert${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jooqDsl.insertInto(${dbTable.jooqDslName}.TABLE)
                        .set(${dbTable.jooqDslName}.TABLE.${dbTable.primaryKeyJooqFieldName}, domainInstance.${dbTable.kotlinModelClass.idFieldName}.value)
                        ${
            StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """.set(${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}, domainInstance.${dbField.kotlinModelField.kotlinFieldName})
                        """}}
                        .execute()
                }
            
                override fun update${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jooqDsl.update(${dbTable.jooqDslName}.TABLE)
                        ${
            StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
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
                        ${
            StringTemplateHelper.forEach(dbTable.tableFields()) { dbField ->
                        """${dbField.kotlinModelField.kotlinFieldName} = record.get(${dbTable.jooqDslName}.TABLE.${dbField.jooqFieldName}),
                        """}}
                    )
                }
            }
        """.identForMarker()
    }
}
