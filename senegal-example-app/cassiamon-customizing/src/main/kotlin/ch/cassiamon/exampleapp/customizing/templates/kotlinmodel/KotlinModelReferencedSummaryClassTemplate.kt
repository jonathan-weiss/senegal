package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelReferencedSummaryClassTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass, kotlinModelReferencedClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        import ${kotlinModelReferencedClass.kotlinPackage}.${kotlinModelReferencedClass.idFieldType}
        
        class ${kotlinModelClass.kotlinClassName}${kotlinModelReferencedClass.kotlinClassName}Summary(
            val ${kotlinModelReferencedClass.idFieldName}: ${kotlinModelReferencedClass.idFieldType},${forEach(kotlinModelReferencedClass.kotlinFields()) { field ->
            """
            var ${field.kotlinFieldName}: ${field.kotlinFieldType},
            """ } }
        )  {
        }
        """.identForMarker()
    }
}
