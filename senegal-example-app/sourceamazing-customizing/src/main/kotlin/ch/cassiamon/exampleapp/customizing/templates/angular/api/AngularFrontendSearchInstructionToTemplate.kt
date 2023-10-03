package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendSearchInstructionToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import {${templateModel.entityName}IdTO} from "./${templateModel.entityFileName}-id-to.model";

        export interface Search${templateModel.entityName}InstructionTO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO | undefined,${
            StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
            """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType} | undefined,"""}}
        }
        """.identForMarker()
    }
}
