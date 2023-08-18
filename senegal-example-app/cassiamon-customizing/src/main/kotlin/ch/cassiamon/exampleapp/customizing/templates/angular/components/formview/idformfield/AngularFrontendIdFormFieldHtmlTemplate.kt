package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendIdFormFieldHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
                <mat-form-field appearance="fill">
                  <mat-label>${templateModel.transferObjectIdFieldName}</mat-label>
                  <input matInput type="text" [formControl]="${templateModel.transferObjectIdFieldName}FormControl">
                </mat-form-field>
        """.identForMarker()
    }
}
