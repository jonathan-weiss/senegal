package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

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
