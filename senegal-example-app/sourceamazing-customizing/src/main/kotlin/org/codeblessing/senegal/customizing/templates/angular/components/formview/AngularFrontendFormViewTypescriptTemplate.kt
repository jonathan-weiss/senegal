package org.codeblessing.senegal.customizing.templates.angular.components.formview

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendFormViewTypescriptTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
            import {FormControl, FormGroup} from "@angular/forms";
            import {MatTabChangeEvent} from "@angular/material/tabs";
            import {ComponentStackService} from "../../../../app/shared/component-stack/component-stack.service";
            import {StackKey} from "../../../../app/shared/component-stack/stack-key";
            import {ErrorMessage} from "../../../../app/shared/error-list/error-message.model";
            import {ErrorTransformationService} from "../../../../app/shared/error-list/error-transformation.service";
            import {Observer} from "rxjs";
            
            import {${templateModel.entityName}FormService} from "./${templateModel.entityFileName}-form.service";
            import {${templateModel.entityName}TO} from "../../api/${templateModel.entityFileName}-to.model";
            
            @Component({
                selector: '${templateModel.entityFileName}-form-view',
                templateUrl: './${templateModel.entityFileName}-form-view.component.html',
                styleUrls: ['./${templateModel.entityFileName}-form-view.component.scss'],
            })
            export class ${templateModel.entityName}FormViewComponent implements OnInit {
            
              @Input() ${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO | undefined;
            
              @Output() saveClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
              @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
            
              @Input() isLocked!: boolean;
              @Input() stackKey!: StackKey
            
              ${templateModel.decapitalizedEntityName}Form: FormGroup = new FormGroup({});
            
              tabCommonsSelected: EventEmitter<void> = new EventEmitter<void>()
            
              errorMessages: Array<ErrorMessage> = []
            
              constructor(private ${templateModel.decapitalizedEntityName}FormService: ${templateModel.entityName}FormService,
                          private componentStackService: ComponentStackService,
                          private errorTransformationService: ErrorTransformationService,
                          ) {
              }
            
              ngOnInit() {
                this.${templateModel.decapitalizedEntityName}FormService.initForm(this.${templateModel.decapitalizedEntityName}Form)
              }
            
              get ${templateModel.transferObjectIdFieldName}FormControl(): FormControl {
                return this.${templateModel.decapitalizedEntityName}FormService.getFormControl(this.${templateModel.decapitalizedEntityName}Form, this.${templateModel.decapitalizedEntityName}FormService.${templateModel.transferObjectIdFieldName}FormControlName);
              };
              ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
              get ${templateModel.decapitalizedEntityName}${angularModelField.fieldName}FormControl(): FormControl {
                return this.${templateModel.decapitalizedEntityName}FormService.getFormControl(this.${templateModel.decapitalizedEntityName}Form, this.${templateModel.decapitalizedEntityName}FormService.${angularModelField.decapitalizedFieldName}FormControlName);
              };""" }}

              isCreateMode(): boolean {
                return this.${templateModel.decapitalizedEntityName} == undefined;
              }
            
              private store${templateModel.entityName}Observer: Partial<Observer<${templateModel.entityName}TO>> = {
                next: ${templateModel.decapitalizedEntityName} => this.afterSuccessfulServerResponse(${templateModel.decapitalizedEntityName}),
                error: error => this.errorCase(this.${templateModel.decapitalizedEntityName}, error)
              }
            
              saveForm(): void {
                if(this.${templateModel.decapitalizedEntityName} == undefined) {
                  this.${templateModel.decapitalizedEntityName}FormService.performCreateOnServer(this.${templateModel.decapitalizedEntityName}Form).subscribe(this.store${templateModel.entityName}Observer);
                } else {
                  this.${templateModel.decapitalizedEntityName}FormService.performUpdateOnServer(this.${templateModel.decapitalizedEntityName}Form, this.${templateModel.decapitalizedEntityName}).subscribe(this.store${templateModel.entityName}Observer);
                }
              }
            
              private errorCase(entry: ${templateModel.entityName}TO | undefined, error: any): void {
                const errorMessage = this.errorTransformationService.transformErrorToMessage(this.entityDescription(entry), error)
            
                if(errorMessage != undefined) {
                  this.errorMessages.push(errorMessage)
                }
              }
            
              private entityDescription(entry: ${templateModel.entityName}TO | undefined): string {
                if(entry == undefined) {
                  return 'The ${templateModel.entityName} could not be created.'
                }
                
                return 'The ${templateModel.entityName} (' + entry.transferObjectDescription + ') could not be stored.'
              }
            
            
              private afterSuccessfulServerResponse(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO) {
                this.saveClicked.emit(${templateModel.decapitalizedEntityName});
                this.componentStackService.removeLatestComponentFromStack(this.stackKey);
              }
            
              cancelForm() {
                this.cancelClicked.emit();
                this.componentStackService.removeLatestComponentFromStack(this.stackKey);
              }
            
            
              isFormValid() {
                return !this.${templateModel.decapitalizedEntityName}Form.invalid;
              }
            
              openTab(tabChangeEvent: MatTabChangeEvent): void {
                switch (tabChangeEvent.index) {
                  case 0: this.tabCommonsSelected.emit(); break;
                }
              }
            }
        """.identForMarker()
    }
}
