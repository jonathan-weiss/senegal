package ch.cassiamon.exampleapp.customizing.templates.angular.panelview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendPanelViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        <h1>${templateModel.entityName}</h1>
        
        <${templateModel.entityFileName}-table-view
            [all${templateModel.entityName}]="all${templateModel.entityName}"
            [tableControlsDisabled]="isEditingMode()"
            (editEntryClicked)="onEdit(${'$'}event)"
            (deleteEntryClicked)="onPerformDelete(${'$'}event)"
        ></${templateModel.entityFileName}-table-view>
        
        
        <div class="action-box">
            <button mat-raised-button color="primary" [disabled]="isEditingMode()" (click)="onNewEntry()">Add new ${templateModel.entityName}...</button>
        </div>
        
        <ng-container *ngIf="update${templateModel.entityName}Instruction != undefined">
            <${templateModel.entityFileName}-edit-view
                [update${templateModel.entityName}Instruction]="update${templateModel.entityName}Instruction!"
                (saveClicked)="onPerformUpdate(${'$'}event)"
                (cancelClicked)="resetEdit()"
            ></${templateModel.entityFileName}-edit-view>
        </ng-container>
        <ng-container *ngIf="create${templateModel.entityName}Instruction != undefined">
            <${templateModel.entityFileName}-add-view
                [create${templateModel.entityName}Instruction]="create${templateModel.entityName}Instruction!"
                (saveClicked)="onPerformCreate(${'$'}event)"
                (cancelClicked)="resetEdit()"
            ></${templateModel.entityFileName}-add-view>
        </ng-container>

        """.identForMarker()
    }
}
