package ${templateModel.RestApiFacadePackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}
import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelIdFieldType}
import ${templateModel.KotlinModelPackage}.Delete${templateModel.KotlinModelClassName}Instruction

import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}

data class Delete${templateModel.RestApiTransferObjectName}InstructionTO(
    val ${templateModel.RestApiTransferObjectIdFieldName}: ${templateModel.RestApiTransferObjectIdFieldType},

) {
    fun toDomain() = Delete${templateModel.KotlinModelClassName}Instruction(
        ${templateModel.KotlinModelIdFieldName} = ${templateModel.KotlinModelIdFieldType}(this.${templateModel.RestApiTransferObjectIdFieldName}.uuid),
    )
}
