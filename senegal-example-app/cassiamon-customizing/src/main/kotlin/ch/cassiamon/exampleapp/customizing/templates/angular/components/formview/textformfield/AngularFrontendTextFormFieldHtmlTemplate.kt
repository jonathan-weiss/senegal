package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.textformfield

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelField
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendTextFormFieldHtmlTemplate {

    fun fillTemplate(angularModelClass: AngularModelClass, angularModelField: AngularModelField): String {
        return """
                <mat-form-field appearance="fill">
                  <mat-label>${angularModelField.fieldName}</mat-label>
                  <input matInput type="text" [formControl]="${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}FormControl">
                  <mat-error *ngIf="${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}FormControl.hasError('required')">
                          ${angularModelField.fieldName} is <strong>required</strong>
                  </mat-error>
                </mat-form-field>
        """.identForMarker()
    }
}
