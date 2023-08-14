package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelSearchInstructionTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
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


