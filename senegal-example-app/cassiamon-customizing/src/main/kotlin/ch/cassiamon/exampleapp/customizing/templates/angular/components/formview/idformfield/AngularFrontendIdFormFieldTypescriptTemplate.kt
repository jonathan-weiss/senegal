package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendIdFormFieldTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {Component, Input, OnInit} from '@angular/core';
            import {FormControl} from "@angular/forms";
            import { ${templateModel.entityName}IdTO } from "../../../api/${templateModel.entityFileName}-id-to.model";
            
            @Component({
                selector: '${templateModel.entityFileName}-id-form-field',
                templateUrl: './${templateModel.entityFileName}-id-form-field.component.html',
                styleUrls: ['./${templateModel.entityFileName}-id-form-field.component.scss'],
            })
            export class ${templateModel.entityName}IdFormFieldComponent implements OnInit {
            
              @Input() ${templateModel.decapitalizedEntityName}IdFormControl!: FormControl;

              @Input() ${templateModel.decapitalizedEntityName}Id: ${templateModel.entityName}IdTO | undefined;

              ngOnInit() {
                this.${templateModel.decapitalizedEntityName}IdFormControl.disable() // id is not editable
                this.${templateModel.decapitalizedEntityName}IdFormControl.patchValue(this.${templateModel.decapitalizedEntityName}Id == undefined ? undefined : this.${templateModel.decapitalizedEntityName}Id.value)
              }
            }

        """.identForMarker()
    }
}
