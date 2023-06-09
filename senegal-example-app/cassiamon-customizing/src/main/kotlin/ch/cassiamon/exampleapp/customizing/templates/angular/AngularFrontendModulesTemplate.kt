package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendModulesTemplate {

    fun fillTemplate(templateModels: List<AngularModelClass>): String {
        return """
            import {NgModule} from '@angular/core';
            import {BrowserModule} from '@angular/platform-browser';
            
            import {MatIconModule} from "@angular/material/icon";
            import {MatStepperModule} from "@angular/material/stepper";
            import {FormsModule, ReactiveFormsModule} from "@angular/forms";
            import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
            import {MatChipsModule} from "@angular/material/chips";
            import {MatSnackBarModule} from "@angular/material/snack-bar";
            import {MatMenuModule} from "@angular/material/menu";
            import {MAT_TABS_CONFIG} from "@angular/material/tabs";
            import {MatDialogModule} from "@angular/material/dialog";
            import {HttpClientModule} from "@angular/common/http";
            import {MatTableModule} from "@angular/material/table";
            import {MatButtonModule} from "@angular/material/button";
            import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
            import {MatInputModule} from "@angular/material/input";
            import {MatCardModule} from "@angular/material/card";
            
            ${StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
            import { ${entityNode.entityName}TableViewComponent } from "./${entityNode.entityFileName}/component/${entityNode.entityFileName}-table-view/${entityNode.entityFileName}-table-view.component"
            import { ${entityNode.entityName}AddViewComponent } from "./${entityNode.entityFileName}/component/${entityNode.entityFileName}-add-view/${entityNode.entityFileName}-add-view.component"
            import { ${entityNode.entityName}EditViewComponent } from "./${entityNode.entityFileName}/component/${entityNode.entityFileName}-edit-view/${entityNode.entityFileName}-edit-view.component"
            import { ${entityNode.entityName}PanelViewComponent } from "./${entityNode.entityFileName}/component/${entityNode.entityFileName}-panel-view/${entityNode.entityFileName}-panel-view.component"
            """}}
            
            
            @NgModule({
                declarations: [${StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
                ${entityNode.entityName}TableViewComponent,
                ${entityNode.entityName}EditViewComponent,
                ${entityNode.entityName}AddViewComponent,
                ${entityNode.entityName}PanelViewComponent,"""}}
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
                ],
                providers: [
                    {provide: MAT_TABS_CONFIG, useValue: {animationDuration: '0ms'}},
                    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
                ],
            })
            export class GeneratedEntitiesModule {
            }
            
        """.identForMarker()
    }
}
