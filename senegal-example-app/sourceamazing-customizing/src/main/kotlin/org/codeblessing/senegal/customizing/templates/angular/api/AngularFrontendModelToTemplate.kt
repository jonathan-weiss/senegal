package org.codeblessing.senegal.customizing.templates.angular.api

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendModelToTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            
        import {${templateModel.entityName}IdTO} from "./${templateModel.entityFileName}-id-to.model";

        export interface ${templateModel.entityName}TO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO,${
            StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
        """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType},"""}}
            
            transferObjectDescription: string,
        }
            
        """.identForMarker()
    }
}
