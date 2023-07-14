package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendModelToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import { UuidTO } from '../../../app/uuid-to.model';
        
        export interface ${templateModel.entityName}TO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.transferObjectIdFieldType},${
            StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
        """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType},"""}}
        }
            
        """.identForMarker()
    }
}
