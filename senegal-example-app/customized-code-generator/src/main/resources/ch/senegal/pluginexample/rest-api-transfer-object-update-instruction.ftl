package ${templateModel.RestApiFacadePackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ${templateModel.KotlinModelPackage}.Update${templateModel.KotlinModelClassName}Instruction


import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}

data class Update${templateModel.RestApiTransferObjectName}InstructionTO(
    val ${templateModel.RestApiTransferObjectIdFieldName}: ${templateModel.RestApiTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.RestApiTransferObjectFieldName}: ${fieldNode.RestApiTransferObjectFieldType},</#list>
) {
    fun toDomain() = Update${templateModel.KotlinModelClassName}Instruction(
        ${templateModel.KotlinModelIdFieldName} = ${templateModel.KotlinModelIdFieldType}(this.${templateModel.RestApiTransferObjectIdFieldName}.uuid),<#list templateModel.childNodes as fieldNode>
        ${fieldNode.KotlinModelFieldName} = this.${fieldNode.RestApiTransferObjectFieldName},</#list>
    )
}


