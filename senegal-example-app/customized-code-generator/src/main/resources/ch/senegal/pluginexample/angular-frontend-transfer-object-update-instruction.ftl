
import { UuidTO } from '../../../generated-openapi';

export interface Update${templateModel.AngularFrontendEntityName}InstructionTO {
    ${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldType},</#list>
}
