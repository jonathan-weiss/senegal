package org.codeblessing.senegal.customizing.templates.angular.components.formview.textformfield

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendTextFormFieldHtmlTemplate {

    fun fillTemplate(angularModelClass: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass, angularModelField: org.codeblessing.senegal.customizing.templates.angular.AngularModelField): String {
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
