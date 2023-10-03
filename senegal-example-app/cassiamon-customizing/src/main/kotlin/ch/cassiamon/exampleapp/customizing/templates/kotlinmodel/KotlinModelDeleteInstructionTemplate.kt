package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object KotlinModelDeleteInstructionTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
            package ${kotlinModelClass.kotlinPackage}
            
            class Delete${kotlinModelClass.kotlinClassName}Instruction(
                val ${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType},
            )
        """.identForMarker()
    }


}
