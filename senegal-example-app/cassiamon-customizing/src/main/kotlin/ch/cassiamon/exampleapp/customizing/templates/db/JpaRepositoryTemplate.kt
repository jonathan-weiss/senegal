package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper.identForMarker

object JpaRepositoryTemplate {

    fun fillTemplate(dbTable: DbTable): String {
        return """
            package ${dbTable.jpaRepositoryPackage}
            
            import org.springframework.data.jpa.repository.JpaRepository
            import org.springframework.stereotype.Repository
            import java.util.UUID
            
            @Repository
            interface ${dbTable.jpaRepositoryName} : JpaRepository<${dbTable.jpaEntityName}, ${dbTable.primaryKeyJpaFieldType}> {
            
            }
        """.identForMarker()
    }
}
