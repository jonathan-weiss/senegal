package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper.forEach

object KotlinModelClassTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
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
