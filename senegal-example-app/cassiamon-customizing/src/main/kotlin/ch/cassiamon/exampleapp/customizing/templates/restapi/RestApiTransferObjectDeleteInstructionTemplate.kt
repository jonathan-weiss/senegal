package ch.cassiamon.exampleapp.customizing.templates.restapi

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object RestApiTransferObjectDeleteInstructionTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Delete${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            
            data class Delete${restModelClass.transferObjectBaseName}InstructionTO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.kotlinModelClass.idFieldType},
            ){
                fun toDomain() = Delete${restModelClass.kotlinModelClass.kotlinClassName}Instruction(
                    ${restModelClass.kotlinModelClass.idFieldName} = this.${restModelClass.transferObjectIdFieldName},
                )
            }
        """.identForMarker()
    }
}
