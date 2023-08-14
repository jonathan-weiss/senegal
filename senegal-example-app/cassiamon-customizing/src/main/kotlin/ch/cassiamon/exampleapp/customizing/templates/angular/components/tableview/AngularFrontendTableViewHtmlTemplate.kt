package ch.cassiamon.exampleapp.customizing.templates.angular.components.tableview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendTableViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
                <table mat-table [dataSource]="all${templateModel.entityName}" class="mat-elevation-z0">
                
                    <ng-container matColumnDef="${templateModel.transferObjectIdFieldName}">
                        <th mat-header-cell *matHeaderCellDef> No. </th>
                        <td mat-cell *matCellDef="let element"> {{as${templateModel.entityName}(element).${templateModel.transferObjectIdFieldName}.value}} </td>
                    </ng-container>
                
              ${StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
                    <ng-container matColumnDef="${angularModelField.decapitalizedFieldName}">
                        <th mat-header-cell *matHeaderCellDef> ${angularModelField.fieldName} </th>
                        <td mat-cell *matCellDef="let element"> {{ as${templateModel.entityName}(element).${angularModelField.decapitalizedFieldName} }} </td>
                    </ng-container>"""}}
                
                    <ng-container matColumnDef="context">
                        <th mat-header-cell *matHeaderCellDef></th>
                        <td mat-cell *matCellDef="let element" class="context-column">
                
                          <button *ngIf="showSelectButton" matSuffix mat-icon-button (click)="selectClicked(element)" [disabled]="isLocked">
                            <mat-icon>check</mat-icon>
                          </button>
                          <button *ngIf="showEditButton" matSuffix mat-icon-button (click)="editClicked(element)" [disabled]="isLocked">
                            <mat-icon>edit</mat-icon>
                          </button>
                          <button *ngIf="showDeleteButton" matSuffix mat-icon-button (click)="deleteClicked(element)" [disabled]="isLocked">
                            <mat-icon>delete</mat-icon>
                          </button>
                        </td>
                    </ng-container>
                
                    <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
                    <tr mat-row *matRowDef="let row; columns: displayedColumns;" (dblclick)="onRowDoubleClicked(row)"
                        [class.highlighted-row]="isHighlighted(row)"></tr>
                </table>
        """.identForMarker()
    }
}
