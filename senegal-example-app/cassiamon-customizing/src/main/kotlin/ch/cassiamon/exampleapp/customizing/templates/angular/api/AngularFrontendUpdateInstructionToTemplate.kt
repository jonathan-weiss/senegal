package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendUpdateInstructionToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import {${templateModel.entityName}IdTO} from "./${templateModel.entityFileName}-id-to.model";

        export interface Update${templateModel.entityName}InstructionTO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO,${
            StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
            """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType},"""}}
        }
        """.identForMarker()
    }
}
