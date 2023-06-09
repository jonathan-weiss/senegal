package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object RestApiTransferObjectCreateInstructionTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.kotlinClassName}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Create${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            
            data class Create${restModelClass.transferObjectBaseName}InstructionTO(${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.transferObjectFieldName}: ${field.transferObjectFieldType},""" }}
            ) {
                fun toDomain() = Create${restModelClass.kotlinModelClass.kotlinClassName}Instruction(${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                """
                    ${field.kotlinModelField.kotlinFieldName} = this.${field.transferObjectFieldName},""" }}
                )
            }    
        """.identForMarker()
    }
}
