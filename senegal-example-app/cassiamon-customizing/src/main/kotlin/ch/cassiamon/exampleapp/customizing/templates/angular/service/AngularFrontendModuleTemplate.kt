package ch.cassiamon.exampleapp.customizing.templates.angular.service

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendModuleTemplate {
    fun fillTemplate(angularModelClass: AngularModelClass): String {
        return """
            import { NgModule }       from '@angular/core';
            
            import {BrowserModule} from '@angular/platform-browser';
            
            import {MatIconModule} from "@angular/material/icon";
            import {MatStepperModule} from "@angular/material/stepper";
            import {FormsModule, ReactiveFormsModule} from "@angular/forms";
            import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
            import {MatChipsModule} from "@angular/material/chips";
            import {MatSnackBarModule} from "@angular/material/snack-bar";
            import {MatMenuModule} from "@angular/material/menu";
            import {MAT_TABS_CONFIG, MatTabsModule} from "@angular/material/tabs";
            import {MatDialogModule} from "@angular/material/dialog";
            import {HttpClientModule} from "@angular/common/http";
            import {MatTableModule} from "@angular/material/table";
            import {MatButtonModule} from "@angular/material/button";
            import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
            import {MatInputModule} from "@angular/material/input";
            import {MatCardModule} from "@angular/material/card";
            import {MatListModule} from "@angular/material/list";
            import {MatDividerModule} from "@angular/material/divider";
            import {MatAutocompleteModule} from "@angular/material/autocomplete";

            import {SharedModule} from "../../app/shared/shared.module";
            
            import { ${angularModelClass.entityName}ApiService } from "./api/${angularModelClass.entityFileName}-api.service";
            import { ${angularModelClass.entityName}Service } from "./${angularModelClass.entityFileName}.service";

            import { ${angularModelClass.entityName}FormViewComponent } from "./components/${angularModelClass.entityFileName}-form-view/${angularModelClass.entityFileName}-form-view.component"
            import { ${angularModelClass.entityName}SearchViewComponent } from "./components/${angularModelClass.entityFileName}-search-view/${angularModelClass.entityFileName}-search-view.component"
            import { ${angularModelClass.entityName}TableViewComponent } from "./components/${angularModelClass.entityFileName}-table-view/${angularModelClass.entityFileName}-table-view.component"
            import { ${angularModelClass.entityName}IdFormFieldComponent } from "./components/${angularModelClass.entityFileName}-form-view/${angularModelClass.entityFileName}-id-form-field/${angularModelClass.entityFileName}-id-form-field.component"
            ${StringTemplateHelper.forEach(angularModelClass.angularFields()) { angularModelField -> """
            import { ${angularModelClass.entityName}${angularModelField.fieldName}FormFieldComponent } from "./components/${angularModelClass.entityFileName}-form-view/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field.component" """}}       

            import { ${angularModelClass.entityName}EntryPointComponent } from "./stack-components/${angularModelClass.entityFileName}-entry-point/${angularModelClass.entityFileName}-entry-point.component"
            import { ${angularModelClass.entityName}FormStackEntryComponent } from "./stack-components/${angularModelClass.entityFileName}-form-stack-entry/${angularModelClass.entityFileName}-form-stack-entry.component"
            import { ${angularModelClass.entityName}SearchStackEntryComponent } from "./stack-components/${angularModelClass.entityFileName}-search-stack-entry/${angularModelClass.entityFileName}-search-stack-entry.component"
            
            @NgModule({
                declarations: [
                    ${angularModelClass.entityName}FormViewComponent,
                    ${angularModelClass.entityName}SearchViewComponent,
                    ${angularModelClass.entityName}TableViewComponent,
                    ${angularModelClass.entityName}IdFormFieldComponent,       
                ${StringTemplateHelper.forEach(angularModelClass.angularFields()) { angularModelField -> """
                    ${angularModelClass.entityName}${angularModelField.fieldName}FormFieldComponent,"""}}       
                    ${angularModelClass.entityName}EntryPointComponent,
                    ${angularModelClass.entityName}FormStackEntryComponent,
                    ${angularModelClass.entityName}SearchStackEntryComponent,
                ],
                imports: [
                    BrowserModule,
                    BrowserAnimationsModule,
                    MatMenuModule,
                    MatFormFieldModule,
                    MatInputModule,
                    MatTableModule,
                    MatIconModule,
                    MatIconModule,
                    MatSnackBarModule,
                    MatDialogModule,
                    MatStepperModule,
                    HttpClientModule,
                    FormsModule,
                    ReactiveFormsModule,
                    MatChipsModule,
                    MatCardModule,
                    MatButtonModule,
                    MatAutocompleteModule,
                    MatTabsModule,
                    MatListModule,
                    MatDividerModule,
                    SharedModule,
                ],
                providers: [
                    {provide: MAT_TABS_CONFIG, useValue: {animationDuration: '0ms'}},
                    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
                ],

            })
            export class ${angularModelClass.entityName}Module {}
            
        """.identForMarker()
    }
}
