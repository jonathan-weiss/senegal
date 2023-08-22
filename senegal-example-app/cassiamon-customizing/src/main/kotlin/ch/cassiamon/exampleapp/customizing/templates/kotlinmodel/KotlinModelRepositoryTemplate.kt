package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelRepositoryTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
       ${
        forEach(kotlinModelClass.referencingFields()) { referencedField ->
        """
        import ${referencedField.kotlinModelClass.kotlinPackage}.${referencedField.kotlinModelClass.idFieldType}
        """}}

        interface ${kotlinModelClass.kotlinClassName}Repository {
            fun fetch${kotlinModelClass.kotlinClassName}ById(${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType}): ${kotlinModelClass.kotlinClassName}
            fun fetchAll${kotlinModelClass.kotlinClassName}(): List<${kotlinModelClass.kotlinClassName}>
            fun fetchAll${kotlinModelClass.kotlinClassName}Filtered(searchTerm: String): List<${kotlinModelClass.kotlinClassName}>
               ${
            forEach(kotlinModelClass.referencingFields()) { referencedField ->
            """
            fun fetchAll${kotlinModelClass.kotlinClassName}By${referencedField.kotlinModelClass.kotlinClassName}(${referencedField.kotlinModelClass.idFieldName}: ${referencedField.kotlinModelClass.idFieldType}): List<${kotlinModelClass.kotlinClassName}>
            
            fun fetch${kotlinModelClass.kotlinClassName}${referencedField.kotlinModelClass.kotlinClassName}SummaryById(${referencedField.kotlinModelClass.idFieldName}: ${referencedField.kotlinModelClass.idFieldType}): ${kotlinModelClass.kotlinClassName}${referencedField.kotlinModelClass.kotlinClassName}Summary
            """}}

        
            fun insert${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
            fun update${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
            fun delete${kotlinModelClass.kotlinClassName}(domainInstance: ${kotlinModelClass.kotlinClassName})
        }
        """.identForMarker()
    }
}
