package org.codeblessing.senegal.customizing.templates.angular.components.formview.textformfield

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendTextFormFieldTypescriptTemplate {

    fun fillTemplate(angularModelClass: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass, angularModelField: org.codeblessing.senegal.customizing.templates.angular.AngularModelField): String {
        return """
            import {Component, Input, OnInit} from '@angular/core';
            import {FormControl, Validators} from "@angular/forms";
            
            @Component({
                selector: '${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field',
                templateUrl: './${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field.component.html',
                styleUrls: ['./${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field.component.scss'],
            })
            export class ${angularModelClass.entityName}${angularModelField.fieldName}FormFieldComponent implements OnInit {
            
              @Input() ${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}FormControl!: FormControl;

              @Input() ${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}: string | undefined;
              @Input() isLocked!: boolean;

              ngOnInit() {
                this.${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}FormControl.setValidators(Validators.required)
                this.${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName}FormControl.patchValue(this.${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName} == undefined ? '' : this.${angularModelClass.decapitalizedEntityName}${angularModelField.fieldName})
              }
            }

        """.identForMarker()
    }
}
