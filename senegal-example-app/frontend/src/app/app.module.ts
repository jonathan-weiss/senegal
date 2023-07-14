import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
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
import {BookTableViewComponent} from "./book/book-table-view/book-table-view.component";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {BookEditViewComponent} from "./book/book-edit-view/book-edit-view.component";
import {BookPanelViewComponent} from "./book/book-panel-view/book-panel-view.component";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {BookAddViewComponent} from "./book/book-add-view/book-add-view.component";
import {SideNavigationComponent} from "./navigation/side-navigation.component";
import {GeneratedEntitiesModule} from "../generated/generated-entities.module";
import {GeneratedEntitiesRoutingModule} from "../generated/generated-entities-routing.module";
import {RouterModule} from "@angular/router";
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatCardModule} from "@angular/material/card";
import {AuthorReadonlyTableViewComponent} from "./author/author-table-view/author-readonly-table-view.component";
import {AuthorPanelViewComponent} from "./author/author-panel-view/author-panel-view.component";


@NgModule({
  declarations: [
    AppComponent,
    AuthorReadonlyTableViewComponent,
    AuthorPanelViewComponent,
    BookTableViewComponent,
    BookAddViewComponent,
    BookEditViewComponent,
    BookPanelViewComponent,
    SideNavigationComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    AppRoutingModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatIconModule,
    MatSnackBarModule,
    MatDialogModule,
    MatStepperModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatButtonModule,
    MatSidenavModule,
    MatCardModule,
    GeneratedEntitiesModule,
    GeneratedEntitiesRoutingModule,
  ],
  providers: [
    {provide: MAT_TABS_CONFIG, useValue: {animationDuration: '0ms'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
  ],

  bootstrap: [AppComponent]
})
export class AppModule {
}
