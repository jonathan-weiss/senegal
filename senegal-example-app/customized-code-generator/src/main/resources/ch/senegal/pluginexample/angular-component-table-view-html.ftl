<table mat-table [dataSource]="all${templateModel.AngularFrontendEntityName}" class="mat-elevation-z8">

    <ng-container matColumnDef="${templateModel.AngularFrontendTransferObjectIdFieldName}">
        <th mat-header-cell *matHeaderCellDef> No. </th>
        <td mat-cell *matCellDef="let element"> {{element.${templateModel.AngularFrontendTransferObjectIdFieldName}.uuid}} </td>
    </ng-container>

    <#list templateModel.childNodes as fieldNode>
    <ng-container matColumnDef="${fieldNode.AngularFrontendTransferObjectFieldName}">
        <th mat-header-cell *matHeaderCellDef> ${fieldNode.AngularFrontendTransferObjectFieldName} </th>
        <td mat-cell *matCellDef="let element"> {{ element.${fieldNode.AngularFrontendTransferObjectFieldName} }} </td>
    </ng-container></#list>

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
