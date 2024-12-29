package org.codeblessing.senegal.codegen.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

object KotlinModelUpdateInstructionTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        class Update${kotlinModelClass.kotlinClassName}Instruction(
            val ${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType},${forEach(kotlinModelClass.kotlinFields()) { field ->
            """
            val ${field.kotlinFieldName}: ${field.kotlinFieldType},""" } }
        )
        """
    }
}
