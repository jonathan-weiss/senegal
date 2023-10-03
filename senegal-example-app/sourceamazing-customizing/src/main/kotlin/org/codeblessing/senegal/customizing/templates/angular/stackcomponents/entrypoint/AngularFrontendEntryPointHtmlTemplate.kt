package org.codeblessing.senegal.customizing.templates.angular.stackcomponents.entrypoint

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendEntryPointHtmlTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            <display-component-stack [stackSelector]="stackKey"></display-component-stack>
        """.identForMarker()
    }
}
