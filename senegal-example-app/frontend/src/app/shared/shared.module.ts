import {NgModule} from '@angular/core';
import {ErrorListComponent} from "./error-list/error-list.component";
import {ComponentStackAnchorDirective} from "./component-stack/component-stack-anchor.directive";
import {SectionComponent} from "./section/section.component";
import {DisplayComponentStackComponent} from "./component-stack/display-component-stack.component";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {BrowserModule} from "@angular/platform-browser";


@NgModule({
  declarations: [
    DisplayComponentStackComponent,
    SectionComponent,
    ComponentStackAnchorDirective,
    ErrorListComponent,
  ],
  imports: [
    BrowserModule,
    MatListModule,
    MatIconModule,

  ],
  exports: [
    DisplayComponentStackComponent,
    SectionComponent,
    ComponentStackAnchorDirective,
    ErrorListComponent,
  ],
})
export class SharedModule {
}
