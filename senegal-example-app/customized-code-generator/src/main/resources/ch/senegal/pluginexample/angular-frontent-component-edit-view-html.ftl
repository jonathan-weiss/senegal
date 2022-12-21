<h2>Edit ${templateModel.AngularFrontendEntityName}</h2>

<mat-card>
    <mat-card-content>
        <mat-form-field appearance="fill">
            <mat-label>${templateModel.AngularFrontendEntityName} Id</mat-label>
            <input matInput type="text" [(ngModel)]="update${templateModel.AngularFrontendEntityName}Instruction.${templateModel.AngularFrontendTransferObjectIdFieldName}.uuid" disabled="true">
        </mat-form-field>
        <#list templateModel.childNodes as fieldNode>
        <mat-form-field appearance="fill">
            <mat-label> ${fieldNode.AngularFrontendTransferObjectFieldName}</mat-label>
            <input matInput type="text" [(ngModel)]="update${templateModel.AngularFrontendEntityName}Instruction.${fieldNode.AngularFrontendTransferObjectFieldName}">
            <button *ngIf="update${templateModel.AngularFrontendEntityName}Instruction.${fieldNode.AngularFrontendTransferObjectFieldName}" matSuffix mat-icon-button aria-label="Clear" (click)="update${templateModel.AngularFrontendEntityName}Instruction.${fieldNode.AngularFrontendTransferObjectFieldName}=${fieldNode.AngularFrontendTransferObjectFieldDefaultValue}">
                <mat-icon>close</mat-icon>
            </button>
        </mat-form-field></#list>
    </mat-card-content>
    <mat-card-actions>
        <button mat-raised-button color="primary" (click)="saveChanges()">Save</button>
        <button mat-raised-button color="secondary" (click)="cancelEdit()">Cancel</button>
    </mat-card-actions>
</mat-card>
