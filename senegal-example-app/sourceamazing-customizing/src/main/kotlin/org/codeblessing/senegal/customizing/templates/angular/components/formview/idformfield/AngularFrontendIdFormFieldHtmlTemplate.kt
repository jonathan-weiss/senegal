package org.codeblessing.senegal.customizing.templates.angular.components.formview.idformfield

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendIdFormFieldHtmlTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
                <mat-form-field appearance="fill">
                  <mat-label>${templateModel.transferObjectIdFieldName}</mat-label>
                  <input matInput type="text" [formControl]="${templateModel.transferObjectIdFieldName}FormControl">
                </mat-form-field>
        """.identForMarker()
    }
}
