package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendUpdateInstructionToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import { UuidTO } from '../../../generated-openapi';


        export interface Update${templateModel.entityName}InstructionTO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.transferObjectIdFieldType},${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
            """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType},"""}}
        }
        """.identForMarker()
    }
}
