package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendIdFormFieldHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
                <mat-form-field appearance="fill">
                  <mat-label>${templateModel.entityName} Id</mat-label>
                  <input matInput type="text" [formControl]="${templateModel.decapitalizedEntityName}IdFormControl">
                </mat-form-field>
        """.identForMarker()
    }
}
