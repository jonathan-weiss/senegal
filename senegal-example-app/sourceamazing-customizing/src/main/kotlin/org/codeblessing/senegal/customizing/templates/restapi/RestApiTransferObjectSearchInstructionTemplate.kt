package org.codeblessing.senegal.customizing.templates.restapi

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object RestApiTransferObjectSearchInstructionTemplate {

    fun fillTemplate(restModelClass: org.codeblessing.senegal.customizing.templates.restapi.RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Search${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            
            data class Search${restModelClass.transferObjectBaseName}InstructionTO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.kotlinModelClass.idFieldType}?,${
            StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.transferObjectFieldName}: ${field.transferObjectFieldType}?,""" }}
            ) {
                fun toDomain() = Search${restModelClass.kotlinModelClass.kotlinClassName}Instruction(
                    ${restModelClass.kotlinModelClass.idFieldName} = this.${restModelClass.transferObjectIdFieldName},${
            StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                """
                    ${field.kotlinModelField.kotlinFieldName} = this.${field.transferObjectFieldName},""" }}
                )
            }    
        """.identForMarker()
    }
}
