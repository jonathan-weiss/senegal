package ch.cassiamon.exampleapp.customizing.templates.angular.panelview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendPanelViewScssTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        .action-box {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        """.identForMarker()
    }
}
