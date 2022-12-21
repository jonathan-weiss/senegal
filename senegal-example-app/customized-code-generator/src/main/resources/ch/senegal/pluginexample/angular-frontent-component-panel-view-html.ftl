<h1>${templateModel.AngularFrontendEntityName}</h1>

<${templateModel.AngularFrontendEntityFilename}-table-view
    [all${templateModel.AngularFrontendEntityName}]="all${templateModel.AngularFrontendEntityName}"
    [tableControlsDisabled]="isEditingMode()"
    (editEntryClicked)="onEdit($event)"
    (deleteEntryClicked)="onPerformDelete($event)"
></${templateModel.AngularFrontendEntityFilename}-table-view>


<div class="action-box">
    <button mat-raised-button color="primary" [disabled]="isEditingMode()" (click)="onNewEntry()">Add new ${templateModel.AngularFrontendEntityName}...</button>
</div>

<ng-container *ngIf="update${templateModel.AngularFrontendEntityName}Instruction != undefined">
    <${templateModel.AngularFrontendEntityFilename}-edit-view
        [update${templateModel.AngularFrontendEntityName}Instruction]="update${templateModel.AngularFrontendEntityName}Instruction!"
        (saveClicked)="onPerformUpdate($event)"
        (cancelClicked)="resetEdit()"
    ></${templateModel.AngularFrontendEntityFilename}-edit-view>
</ng-container>
<ng-container *ngIf="create${templateModel.AngularFrontendEntityName}Instruction != undefined">
    <${templateModel.AngularFrontendEntityFilename}-add-view
        [create${templateModel.AngularFrontendEntityName}Instruction]="create${templateModel.AngularFrontendEntityName}Instruction!"
        (saveClicked)="onPerformCreate($event)"
        (cancelClicked)="resetEdit()"
    ></${templateModel.AngularFrontendEntityFilename}-add-view>
</ng-container>

