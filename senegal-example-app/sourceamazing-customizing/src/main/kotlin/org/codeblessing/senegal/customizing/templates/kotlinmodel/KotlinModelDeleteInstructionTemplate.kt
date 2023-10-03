package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object KotlinModelDeleteInstructionTemplate {

    fun fillTemplate(kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass): String {
        return """
            package ${kotlinModelClass.kotlinPackage}
            
            class Delete${kotlinModelClass.kotlinClassName}Instruction(
                val ${kotlinModelClass.idFieldName}: ${kotlinModelClass.idFieldType},
            )
        """.identForMarker()
    }


}
