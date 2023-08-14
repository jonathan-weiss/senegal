package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object RestApiTransferObjectUpdateInstructionTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Update${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            
            data class Update${restModelClass.transferObjectBaseName}InstructionTO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.kotlinModelClass.idFieldType},${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.transferObjectFieldName}: ${field.transferObjectFieldType},""" }}
            ) {
                fun toDomain() = Update${restModelClass.kotlinModelClass.kotlinClassName}Instruction(
                    ${restModelClass.kotlinModelClass.idFieldName} = this.${restModelClass.transferObjectIdFieldName},${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                """
                    ${field.kotlinModelField.kotlinFieldName} = this.${field.transferObjectFieldName},""" }}
                )
            }    
        """.identForMarker()
    }
}
