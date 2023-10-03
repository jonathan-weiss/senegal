package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendModelToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
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
