package ch.cassiamon.exampleapp.customizing.templates.angular.addview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendAddViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            <h2>Add ${templateModel.entityName}</h2>
            
            <mat-card>
                <mat-card-content>${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
                """
                    <mat-form-field appearance="fill">
                        <mat-label> ${fieldNode.transferObjectFieldName}</mat-label>
                        <input matInput type="text" [(ngModel)]="create${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}">
                        <button *ngIf="create${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}" matSuffix mat-icon-button aria-label="Clear" (click)="create${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}=${fieldNode.transferObjectFieldDefaultValue}">
                            <mat-icon>close</mat-icon>
                        </button>
                    </mat-form-field>"""}}
                </mat-card-content>
                <mat-card-actions>
                    <button mat-raised-button color="primary" (click)="saveChanges()">Save</button>
                    <button mat-raised-button color="secondary" (click)="cancelEdit()">Cancel</button>
                </mat-card-actions>
            </mat-card>

        """.identForMarker()
    }
}
