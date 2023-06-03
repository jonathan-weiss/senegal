package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelRepositoryTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        interface ${kotlinModelClass.kotlinClassName}Repository {
            fun fetch${kotlinModelClass.kotlinClassName}ById(${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType}): ${kotlinModelClass.kotlinClassName}
            fun fetchAll${kotlinModelClass.kotlinClassName}(): List<${kotlinModelClass.kotlinClassName}>
        
            fun insert${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
            fun update${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
            fun delete${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
        }
        """.identForMarker()
    }
}
