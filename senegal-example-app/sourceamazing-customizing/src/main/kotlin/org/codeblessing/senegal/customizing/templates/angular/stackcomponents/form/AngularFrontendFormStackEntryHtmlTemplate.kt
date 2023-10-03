package org.codeblessing.senegal.customizing.templates.angular.stackcomponents.form

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendFormStackEntryHtmlTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            <${templateModel.entityFileName}-form-view
              [${templateModel.decapitalizedEntityName}]="${templateModel.decapitalizedEntityName}"
              (saveClicked)="saveClicked.emit(${'$'}event)"
              (cancelClicked)="cancelClicked.emit(${'$'}event)"
              [isLocked]="isLocked"
              [stackKey]="stackKey"
            ></${templateModel.entityFileName}-form-view>

        """.identForMarker()
    }
}
