package org.codeblessing.senegal.customizing.templates.angular.components.formview

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendFormViewHtmlTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
                <form [formGroup]="${templateModel.decapitalizedEntityName}Form">
                  <mat-card>
                      <mat-card-header>
                        <mat-card-title *ngIf="isCreateMode()">Add ${templateModel.entityName}</mat-card-title>
                        <mat-card-title *ngIf="!isCreateMode()">Edit ${templateModel.entityName}</mat-card-title>
                      </mat-card-header>
                      <mat-card-content>
                        <mat-tab-group (selectedTabChange)="openTab(${'$'}event)">
                          <mat-tab label="Commons">
                            <div class="tab-content">
                              <section *ngIf="!isCreateMode()">
                                <${templateModel.transferObjectIdFieldFileName}-form-field [${templateModel.transferObjectIdFieldName}FormControl]="${templateModel.transferObjectIdFieldName}FormControl" [${templateModel.transferObjectIdFieldName}]="${templateModel.decapitalizedEntityName}?.${templateModel.transferObjectIdFieldName}"></${templateModel.transferObjectIdFieldFileName}-form-field>
                              </section>
                              <section>
                          ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
                                <${templateModel.entityFileName}-${angularModelField.fieldFileName}-form-field [${templateModel.decapitalizedEntityName}${angularModelField.fieldName}FormControl]="${templateModel.decapitalizedEntityName}${angularModelField.fieldName}FormControl" [${templateModel.decapitalizedEntityName}${angularModelField.fieldName}]="${templateModel.decapitalizedEntityName}?.${angularModelField.transferObjectFieldName}" [isLocked]="isLocked"></${templateModel.entityFileName}-${angularModelField.fieldFileName}-form-field>
                                  """ }}
                              </section>
                            </div>
                          </mat-tab>
                        </mat-tab-group>
                      </mat-card-content>
                      <mat-card-actions>
                          <error-list [errorMessages]="errorMessages"></error-list>
                          <button mat-raised-button color="primary" type="submit" (click)="saveForm()" [disabled]="!isFormValid() || isLocked">Save</button>
                          <button mat-raised-button color="secondary" (click)="cancelForm()" [disabled]="isLocked">Cancel</button>
                      </mat-card-actions>
                  </mat-card>
                </form>
        """.identForMarker()
    }
}
