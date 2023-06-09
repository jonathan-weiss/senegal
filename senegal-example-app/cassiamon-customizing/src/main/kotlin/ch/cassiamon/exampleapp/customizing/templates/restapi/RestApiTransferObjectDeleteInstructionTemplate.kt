package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object RestApiTransferObjectDeleteInstructionTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Delete${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            
            data class Delete${restModelClass.transferObjectBaseName}InstructionTO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.transferObjectIdFieldTypeName},
            ){
                fun toDomain() = Delete${restModelClass.kotlinModelClass.kotlinClassName}Instruction(
                    ${restModelClass.kotlinModelClass.idFieldName} = ${restModelClass.kotlinModelClass.idFieldType}(this.${restModelClass.transferObjectIdFieldName}.uuid),
                )
            }
        """.identForMarker()
    }
}
