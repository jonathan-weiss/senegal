package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendIdModelToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        export interface ${templateModel.entityName}IdTO {
            value: string;
        }
            
        """.identForMarker()
    }
}
