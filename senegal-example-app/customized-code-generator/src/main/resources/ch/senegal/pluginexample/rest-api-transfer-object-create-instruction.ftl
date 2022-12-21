package ${templateModel.RestApiFacadePackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}
import ${templateModel.KotlinModelPackage}.Create${templateModel.KotlinModelClassName}Instruction

import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}

data class Create${templateModel.RestApiTransferObjectName}InstructionTO(<#list templateModel.childNodes as fieldNode>
    val ${fieldNode.RestApiTransferObjectFieldName}: ${fieldNode.RestApiTransferObjectFieldType},</#list>
) {
    fun toDomain() = Create${templateModel.KotlinModelClassName}Instruction(<#list templateModel.childNodes as fieldNode>
        ${fieldNode.KotlinModelFieldName} = this.${fieldNode.RestApiTransferObjectFieldName},</#list>
    )
}



