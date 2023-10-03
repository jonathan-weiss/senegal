package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper.forEach

object KotlinModelClassTemplate {

    fun fillTemplate(kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}
        
        class ${kotlinModelClass.kotlinClassName}(
            val ${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType},${forEach(kotlinModelClass.kotlinFields()) { field ->
            """
            var ${field.kotlinFieldName}: ${field.kotlinFieldType},
            """ } }
        )  {
            companion object {
                internal fun create(instruction: Create${kotlinModelClass.kotlinClassName}Instruction) = ${kotlinModelClass.kotlinClassName}(
                    ${kotlinModelClass.idFieldName} = ${kotlinModelClass.idFieldType}.random(),${forEach(kotlinModelClass.kotlinFields()) { field ->
                    """
                    ${field.kotlinFieldName} = instruction.${field.kotlinFieldName},""" } }
                )
            }
        
            internal fun update(instruction: Update${kotlinModelClass.kotlinClassName}Instruction) {${forEach(kotlinModelClass.kotlinFields()) { field ->
                """
                this.${field.kotlinFieldName} = instruction.${field.kotlinFieldName}
                """ } }
            }
        }
        """.identForMarker()
    }
}
