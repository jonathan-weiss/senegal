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

<#list templateModel.childNodes as entityNode>
import { ${entityNode.AngularFrontendEntityName}PanelViewComponent } from "./${entityNode.AngularFrontendEntityFileName}/component/${entityNode.AngularFrontendEntityFileName}-panel-view/${entityNode.AngularFrontendEntityFileName}-panel-view.component"
import { ${entityNode.AngularFrontendEntityName}TableViewComponent } from "./${entityNode.AngularFrontendEntityFileName}/component/${entityNode.AngularFrontendEntityFileName}-table-view/${entityNode.AngularFrontendEntityFileName}-table-view.component"
</#list>


@NgModule({
    declarations: [<#list templateModel.childNodes as entityNode>
    ${entityNode.AngularFrontendEntityName}TableViewComponent,
    ${entityNode.AngularFrontendEntityName}PanelViewComponent,</#list>
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
        MatButtonModule,
    ],
    providers: [
        {provide: MAT_TABS_CONFIG, useValue: {animationDuration: '0ms'}},
        {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
    ],
})
export class GeneratedEntitiesModule {
}
