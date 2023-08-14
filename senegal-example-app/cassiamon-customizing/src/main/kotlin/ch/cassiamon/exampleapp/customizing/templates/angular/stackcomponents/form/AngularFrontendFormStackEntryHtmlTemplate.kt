package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.form

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendFormStackEntryHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
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
