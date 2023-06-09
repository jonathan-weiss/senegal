package ch.cassiamon.exampleapp.customizing.templates.angular.editview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendEditViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            <h2>Edit ${templateModel.entityName}</h2>
            
            <mat-card>
                <mat-card-content>
                    <mat-form-field appearance="fill">
                        <mat-label>${templateModel.entityName} Id</mat-label>
                        <input matInput type="text" [(ngModel)]="update${templateModel.entityName}Instruction.${templateModel.transferObjectIdFieldName}.uuid" disabled="true">
                    </mat-form-field>
                    ${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
                """
                    <mat-form-field appearance="fill">
                        <mat-label> ${fieldNode.transferObjectFieldName}</mat-label>
                        <input matInput type="text" [(ngModel)]="update${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}">
                        <button *ngIf="update${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}" matSuffix mat-icon-button aria-label="Clear" (click)="update${templateModel.entityName}Instruction.${fieldNode.transferObjectFieldName}=${fieldNode.transferObjectFieldDefaultValue}">
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
