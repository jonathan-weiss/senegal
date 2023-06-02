package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper.identForMarker

object RepositoryImplTemplate {

    fun createRepositoryImplementationTemplate(dbTable: DbTable): String {
        return """
            package ${dbTable.repositoryImplementationPackage}
            
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinClassName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.kotlinRepositoryName}
            import ${dbTable.kotlinModelClass.kotlinPackage}.${dbTable.kotlinModelClass.idFieldType}
            import org.springframework.data.repository.findByIdOrNull
            import org.springframework.stereotype.Repository
            
            
            @Repository
            class ${dbTable.repositoryImplementationName}(
            private val jpaRepository: ${dbTable.jpaRepositoryName}
            ) : ${dbTable.kotlinModelClass.kotlinRepositoryName} {
            
                override fun fetch${dbTable.kotlinModelClass.kotlinClassName}ById(${dbTable.kotlinModelClass.idFieldName}: ${dbTable.kotlinModelClass.idFieldType}): ${dbTable.kotlinModelClass.kotlinClassName} {
                    val entry = jpaRepository.findByIdOrNull(${dbTable.kotlinModelClass.idFieldName}.value) ?: throw RuntimeException("${dbTable.kotlinModelClass.kotlinClassName} with id \${'$'}${dbTable.kotlinModelClass.idFieldName} not found")
                    return entry.toDomain()
                }
            
                override fun fetchAll${dbTable.kotlinModelClass.kotlinClassName}(): List<${dbTable.kotlinModelClass.kotlinClassName}> {
                    return jpaRepository.findAll().map { it.toDomain() }
                }
            
                override fun insert${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jpaRepository.save(${dbTable.jpaEntityName}.fromDomain(domainInstance))
                }
            
                override fun update${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jpaRepository.save(${dbTable.jpaEntityName}.fromDomain(domainInstance))
                }
            
                override fun delete${dbTable.kotlinModelClass.kotlinClassName}(domainInstance: ${dbTable.kotlinModelClass.kotlinClassName}) {
                    jpaRepository.delete(${dbTable.jpaEntityName}.fromDomain(domainInstance))
                }
            }
        """.identForMarker()
    }
}
