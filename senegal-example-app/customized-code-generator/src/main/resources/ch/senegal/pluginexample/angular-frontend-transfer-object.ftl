
import { UuidTO } from '../../generated-openapi';

export interface ${templateModel.AngularFrontendTransferObjectName} {
    ${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldType},</#list>
}
