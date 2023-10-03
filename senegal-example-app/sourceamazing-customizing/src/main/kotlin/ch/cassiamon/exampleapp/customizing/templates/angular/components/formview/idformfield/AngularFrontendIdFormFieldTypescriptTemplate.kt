package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendIdFormFieldTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {Component, Input, OnInit} from '@angular/core';
            import {FormControl} from "@angular/forms";
            import { ${templateModel.entityName}IdTO } from "../../../api/${templateModel.entityFileName}-id-to.model";
            
            @Component({
                selector: '${templateModel.transferObjectIdFieldFileName}-form-field',
                templateUrl: './${templateModel.transferObjectIdFieldFileName}-form-field.component.html',
                styleUrls: ['./${templateModel.transferObjectIdFieldFileName}-form-field.component.scss'],
            })
            export class ${templateModel.transferObjectIdFieldName}FormFieldComponent implements OnInit {
            
              @Input() ${templateModel.transferObjectIdFieldName}FormControl!: FormControl;

              @Input() ${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO | undefined;

              ngOnInit() {
                this.${templateModel.transferObjectIdFieldName}FormControl.disable() // id is not editable
                this.${templateModel.transferObjectIdFieldName}FormControl.patchValue(this.${templateModel.transferObjectIdFieldName} == undefined ? undefined : this.${templateModel.transferObjectIdFieldName}.value)
              }
            }

        """.identForMarker()
    }
}
