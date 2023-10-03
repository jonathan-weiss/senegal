package org.codeblessing.senegal.customizing.templates.angular.api

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendDeleteInstructionToTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """

        import {${templateModel.entityName}IdTO} from "./${templateModel.entityFileName}-id-to.model";
        
        export interface Delete${templateModel.entityName}InstructionTO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO,
        }
        """.identForMarker()
    }
}
