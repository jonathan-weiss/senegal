package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendIdModelToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        export interface ${templateModel.entityName}IdTO {
            value: string;
        }
            
        """.identForMarker()
    }
}
