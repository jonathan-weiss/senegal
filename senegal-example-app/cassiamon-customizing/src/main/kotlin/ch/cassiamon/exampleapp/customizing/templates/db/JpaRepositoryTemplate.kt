package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object JpaRepositoryTemplate {

    fun createJpaRepositoryTemplate(dbTable: DbTable): String {
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
