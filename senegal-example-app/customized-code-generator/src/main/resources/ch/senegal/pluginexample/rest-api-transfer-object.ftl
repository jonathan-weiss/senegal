package ${templateModel.RestApiFacadePackage}

import ${templateModel.KotlinModelPackage}.${templateModel.KotlinModelClassName}

import ch.senegal.example.frontendapi.controller.commons.${templateModel.RestApiTransferObjectIdFieldType}

data class ${templateModel.RestApiTransferObjectName}TO(
    val ${templateModel.RestApiTransferObjectIdFieldName}: ${templateModel.RestApiTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    var ${fieldNode.RestApiTransferObjectFieldName}: ${fieldNode.RestApiTransferObjectFieldType},</#list>
) {
    companion object {
        internal fun fromDomain(domainInstance: ${templateModel.KotlinModelClassName}) = ${templateModel.RestApiTransferObjectName}TO(
            ${templateModel.RestApiTransferObjectIdFieldName} = ${templateModel.RestApiTransferObjectIdFieldType}(domainInstance.${templateModel.KotlinModelIdFieldName}.value),<#list templateModel.childNodes as fieldNode>
            ${fieldNode.RestApiTransferObjectFieldName} = domainInstance.${fieldNode.KotlinModelFieldName},</#list>
        )
    }
}


