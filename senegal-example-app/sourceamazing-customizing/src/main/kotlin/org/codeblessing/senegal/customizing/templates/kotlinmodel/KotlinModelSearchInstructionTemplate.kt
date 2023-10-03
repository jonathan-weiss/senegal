package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

object KotlinModelSearchInstructionTemplate {

    fun fillTemplate(kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        class Search${kotlinModelClass.kotlinClassName}Instruction(
            val ${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType}?,${forEach(kotlinModelClass.kotlinFields()) { field ->
            """
            val ${field.kotlinFieldName}: ${field.kotlinFieldType}?,""" } }
        )
        """.identForMarker()
    }
}


