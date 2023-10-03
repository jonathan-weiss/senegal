package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

object KotlinModelCreateInstructionTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        class Create${kotlinModelClass.kotlinClassName}Instruction(${forEach(kotlinModelClass.kotlinFields()) { field ->
            """
            val ${field.kotlinFieldName}: ${field.kotlinFieldType},""" } }
        )
        """.identForMarker()
    }
}
