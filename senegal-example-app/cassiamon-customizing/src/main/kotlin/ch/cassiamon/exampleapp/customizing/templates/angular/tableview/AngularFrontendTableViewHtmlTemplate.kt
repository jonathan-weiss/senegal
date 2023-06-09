package ch.cassiamon.exampleapp.customizing.templates.angular.tableview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendTableViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        <table mat-table [dataSource]="all${templateModel.entityName}" class="mat-elevation-z8">
        
            <ng-container matColumnDef="${templateModel.transferObjectIdFieldName}">
                <th mat-header-cell *matHeaderCellDef> No. </th>
                <td mat-cell *matCellDef="let element"> {{element.${templateModel.transferObjectIdFieldName}.uuid}} </td>
            </ng-container>
        
            ${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
            """
            <ng-container matColumnDef="${fieldNode.transferObjectFieldName}">
                <th mat-header-cell *matHeaderCellDef> ${fieldNode.transferObjectFieldName} </th>
                <td mat-cell *matCellDef="let element"> {{ element.${fieldNode.transferObjectFieldName} }} </td>
            </ng-container>"""}}
        
            <ng-container matColumnDef="context">
                <th mat-header-cell *matHeaderCellDef></th>
                <td mat-cell *matCellDef="let element">
                    <button mat-icon-button [matMenuTriggerFor]="menu" aria-label="Context menu" [disabled]="tableControlsDisabled">
                        <mat-icon>more_vert</mat-icon>
                    </button>
                    <mat-menu #menu="matMenu">
                        <button mat-menu-item (click)="onCtxEditClicked(element)">
                            <mat-icon>edit</mat-icon>
                            <span>Edit entry</span>
                        </button>
                        <button mat-menu-item (click)="onCtxDeleteClicked(element)">
                            <mat-icon>delete</mat-icon>
                            <span>Delete entry</span>
                        </button>
                    </mat-menu>
        
        
                </td>
            </ng-container>
        
            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;" (dblclick)="onRowDoubleClicked(row)"></tr>
        </table>

        """.identForMarker()
    }
}
