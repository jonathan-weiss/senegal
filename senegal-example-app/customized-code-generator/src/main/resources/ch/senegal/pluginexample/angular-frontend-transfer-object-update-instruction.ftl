
import { UuidTO } from '../../../generated-openapi';

export interface Create${templateModel.AngularFrontendEntityName}InstructionTO {
    ${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldType},</#list>
}
