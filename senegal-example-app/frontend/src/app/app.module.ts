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
import {MAT_TABS_CONFIG, MatTabsModule} from "@angular/material/tabs";
import {MatDialogModule} from "@angular/material/dialog";
import {HttpClientModule} from "@angular/common/http";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {SideNavigationComponent} from "./navigation/side-navigation.component";
import {GeneratedEntitiesModule} from "../generated/generated-entities.module";
import {GeneratedEntitiesRoutingModule} from "../generated/generated-entities-routing.module";
import {RouterModule} from "@angular/router";
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatCardModule} from "@angular/material/card";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {AuthorPanelViewComponent} from "./author/components/author-panel-view/author-panel-view.component";
import {AuthorTableViewComponent} from "./author/components/author-table-view/author-table-view.component";
import {DisplayComponentStackComponent} from "./component-stack/display-component-stack.component";
import {ComponentStackAnchorDirective} from "./component-stack/component-stack-anchor.directive";
import {SectionComponent} from "./section/section.component";
import {BookFormViewComponent} from "./book/components/book-form-view/book-form-view.component";
import {
  BookNameFormFieldComponent
} from "./book/components/book-form-view/book-name-form-field/book-name-form-field.component";
import {
  BookIdFormFieldComponent
} from "./book/components/book-form-view/book-id-form-field/book-id-form-field.component";
import {
  MainAuthorFormFieldComponent
} from "./book/components/book-form-view/main-author-form-field/main-author-form-field.component";
import {
  AuthorFirstnameFormFieldComponent
} from "./author/components/author-form-view/author-firstname-form-field/author-firstname-form-field.component";
import {
  AuthorIdFormFieldComponent
} from "./author/components/author-form-view/author-id-form-field/author-id-form-field.component";
import {
  AuthorLastnameFormFieldComponent
} from "./author/components/author-form-view/author-lastname-form-field/author-lastname-form-field.component";
import {AuthorFormViewComponent} from "./author/components/author-form-view/author-form-view.component";
import {
  AllBookByAuthorComponent
} from "./author/components/author-form-view/author-all-book-by-author/all-book-by-author.component";
import {AuthorSearchViewComponent} from "./author/components/author-search-view/author-search-view.component";
import {BookTableViewComponent} from "./book/components/book-table-view/book-table-view.component";
import {BookPanelViewComponent} from "./book/components/book-panel-view/book-panel-view.component";
import {BookSearchViewComponent} from "./book/components/book-search-view/book-search-view.component";


@NgModule({
  declarations: [
    AppComponent,
    DisplayComponentStackComponent,
    SectionComponent,
    ComponentStackAnchorDirective,
    AuthorTableViewComponent,
    AuthorPanelViewComponent,
    AuthorSearchViewComponent,
    AuthorFirstnameFormFieldComponent,
    AuthorIdFormFieldComponent,
    AuthorLastnameFormFieldComponent,
    AuthorFormViewComponent,
    AllBookByAuthorComponent,
    BookFormViewComponent,
    MainAuthorFormFieldComponent,
    BookNameFormFieldComponent,
    BookIdFormFieldComponent,
    BookTableViewComponent,
    BookPanelViewComponent,
    BookSearchViewComponent,
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
    MatAutocompleteModule,
    MatTabsModule,
  ],
  providers: [
    {provide: MAT_TABS_CONFIG, useValue: {animationDuration: '0ms'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
  ],

  bootstrap: [AppComponent]
})
export class AppModule {
}
