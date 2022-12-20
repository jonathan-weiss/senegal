import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookPanelViewComponent} from "./book/book-panel-view/book-panel-view.component";

const routes: Routes = [
  { path: 'books', component: BookPanelViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
