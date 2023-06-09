package ch.cassiamon.exampleapp.customizing.templates.restapi

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object RestApiTransferObjectUpdateInstructionTemplate {

    fun fillTemplate(restModelClass: RestModelClass): String {
        return """
            package ${restModelClass.facadePackageName}
                        
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.kotlinClassName}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.${restModelClass.kotlinModelClass.idFieldType}
            import ${restModelClass.kotlinModelClass.kotlinPackage}.Update${restModelClass.kotlinModelClass.kotlinClassName}Instruction
            
            import ch.senegal.example.frontendapi.controller.commons.${restModelClass.transferObjectIdFieldTypeName}
            
            data class Update${restModelClass.transferObjectBaseName}InstructionTO(
                val ${restModelClass.transferObjectIdFieldName}: ${restModelClass.transferObjectIdFieldTypeName},${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
            """
                val ${field.transferObjectFieldName}: ${field.transferObjectFieldType},""" }}
            ) {
                fun toDomain() = Update${restModelClass.kotlinModelClass.kotlinClassName}Instruction(
                    ${restModelClass.kotlinModelClass.idFieldName} = ${restModelClass.kotlinModelClass.idFieldType}(this.${restModelClass.transferObjectIdFieldName}.uuid),${StringTemplateHelper.forEach(restModelClass.fields()) { field ->
                """
                    ${field.kotlinModelField.kotlinFieldName} = this.${field.transferObjectFieldName},""" }}
                )
            }    
        """.identForMarker()
    }
}
