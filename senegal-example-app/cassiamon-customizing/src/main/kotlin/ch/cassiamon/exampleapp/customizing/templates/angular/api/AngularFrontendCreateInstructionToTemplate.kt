package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendCreateInstructionToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        export interface Create${templateModel.entityName}InstructionTO {${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
            """
            ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldType},"""}}
        }
           
        """.identForMarker()
    }
}
