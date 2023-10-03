package ch.cassiamon.exampleapp.customizing.templates.angular.components.searchview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendSearchViewHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            <mat-card>
              <mat-card-header><mat-card-title>${templateModel.entityName}</mat-card-title></mat-card-header>
              <mat-card-content>
                  <${templateModel.entityFileName}-table-view
                  [all${templateModel.entityName}]="all${templateModel.entityName}"
                  (selectEntryClicked)="select(${'$'}event)"
                  (editEntryClicked)="edit(${'$'}event)"
                  (deleteEntryClicked)="delete(${'$'}event)"
                  [highlighted${templateModel.entityName}]="highlighted${templateModel.entityName}"
                  [showDeleteButton]="showDeleteButton"
                  [showEditButton]="showEditButton"
                  [showSelectButton]="showSelectButton"
                  [isLocked]="isLocked"
                  [stackKey]="stackKey"
                ></${templateModel.entityFileName}-table-view>
                <error-list [errorMessages]="errorMessages"></error-list>
              </mat-card-content>
              <mat-card-actions>
                <button *ngIf="showCancelButton" mat-raised-button color="secondary" (click)="cancel()">Cancel</button>
                <button *ngIf="showAddButton" mat-raised-button color="primary" [disabled]="isLocked" (click)="add()">Add new ${templateModel.entityName}...</button>
              </mat-card-actions>
            </mat-card>
        """.identForMarker()
    }
}
