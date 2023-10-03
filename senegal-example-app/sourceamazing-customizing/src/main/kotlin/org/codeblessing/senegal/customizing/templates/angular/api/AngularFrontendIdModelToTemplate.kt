package org.codeblessing.senegal.customizing.templates.angular.api

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendIdModelToTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """

        export interface ${templateModel.entityName}IdTO {
            value: string;
        }
            
        """.identForMarker()
    }
}
