package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.entrypoint

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendEntryPointHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            <display-component-stack [stackSelector]="stackKey"></display-component-stack>
        """.identForMarker()
    }
}
