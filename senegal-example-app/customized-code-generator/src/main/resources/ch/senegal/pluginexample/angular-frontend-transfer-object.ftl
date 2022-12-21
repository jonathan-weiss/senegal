
import { UuidTO } from '../../../generated-openapi';

export interface ${templateModel.AngularFrontendEntityName}TO {
    ${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType},<#list templateModel.childNodes as fieldNode>
    ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldType},</#list>
}
