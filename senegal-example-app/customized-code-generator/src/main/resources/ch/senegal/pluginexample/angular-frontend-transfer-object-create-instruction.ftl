
export interface Create${templateModel.AngularFrontendEntityName}InstructionTO {<#list templateModel.childNodes as fieldNode>
    ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldType},</#list>
}
