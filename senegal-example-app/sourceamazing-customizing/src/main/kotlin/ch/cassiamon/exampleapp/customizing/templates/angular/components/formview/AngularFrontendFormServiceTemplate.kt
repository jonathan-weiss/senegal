package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendFormServiceTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {Injectable} from '@angular/core';
            import {Observable} from 'rxjs';
            import {FormControl, FormGroup} from "@angular/forms";
            import {${templateModel.entityName}Service} from "../../${templateModel.entityFileName}.service";
            import {Update${templateModel.entityName}InstructionTO} from "../../api/update-${templateModel.entityFileName}-instruction-to.model";
            import {Create${templateModel.entityName}InstructionTO} from "../../api/create-${templateModel.entityFileName}-instruction-to.model";
            import {${templateModel.entityName}TO} from "../../api/${templateModel.entityFileName}-to.model";


            @Injectable({
              providedIn: 'root',
            })
            export class ${templateModel.entityName}FormService {
            
              constructor(private readonly ${templateModel.decapitalizedEntityName}Service: ${templateModel.entityName}Service) {
              }
            
              ${templateModel.transferObjectIdFieldName}FormControlName: string = "${templateModel.transferObjectIdFieldName}";
              ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
              ${angularModelField.decapitalizedFieldName}FormControlName: string = "${angularModelField.decapitalizedFieldName}";"""}}
            
              initForm(${templateModel.decapitalizedEntityName}Form: FormGroup): void {
                ${templateModel.decapitalizedEntityName}Form.addControl(this.${templateModel.transferObjectIdFieldName}FormControlName, new FormControl());
                ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
                ${templateModel.decapitalizedEntityName}Form.addControl(this.${angularModelField.decapitalizedFieldName}FormControlName, new FormControl());"""
                }}
              }
            
              getFormControl(${templateModel.decapitalizedEntityName}Form: FormGroup, formControlName: string): FormControl {
                const control = ${templateModel.decapitalizedEntityName}Form.get(formControlName);
                if(control == undefined) {
                  throw new Error("No control with name '"+formControlName+"' found in ${templateModel.decapitalizedEntityName} form " + ${templateModel.decapitalizedEntityName}Form)
                }
                return control as FormControl;
              }

              ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
              private get${angularModelField.fieldName}FormValue(${templateModel.decapitalizedEntityName}Form: FormGroup): string {
                return this.getFormControl(${templateModel.decapitalizedEntityName}Form, this.${angularModelField.decapitalizedFieldName}FormControlName).value as string;
              }"""
              }}

              performCreateOnServer(${templateModel.decapitalizedEntityName}Form: FormGroup): Observable<${templateModel.entityName}TO> {
                const createInstruction: Create${templateModel.entityName}InstructionTO = {
                
              ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
                  ${angularModelField.transferObjectFieldName}: this.get${angularModelField.fieldName}FormValue(${templateModel.decapitalizedEntityName}Form),"""
              }}
                }
            
                return this.${templateModel.decapitalizedEntityName}Service.create${templateModel.entityName}(createInstruction);
              }
            
              performUpdateOnServer(${templateModel.decapitalizedEntityName}Form: FormGroup, ${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): Observable<${templateModel.entityName}TO> {
                const updateInstruction: Update${templateModel.entityName}InstructionTO = {
                  ${templateModel.transferObjectIdFieldName}: ${templateModel.decapitalizedEntityName}.${templateModel.transferObjectIdFieldName},
              ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
                  ${angularModelField.transferObjectFieldName}: this.get${angularModelField.fieldName}FormValue(${templateModel.decapitalizedEntityName}Form),"""
              }}
                }
                return this.${templateModel.decapitalizedEntityName}Service.update${templateModel.entityName}(updateInstruction);
              }
            
            }
        """.identForMarker()
    }
}
