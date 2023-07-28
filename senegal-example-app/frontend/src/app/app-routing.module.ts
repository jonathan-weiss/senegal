import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookPanelViewComponent} from "./book/book-panel-view/book-panel-view.component";
import {AuthorPanelViewComponent} from "./author/component/author-panel-view/author-panel-view.component";

const routes: Routes = [
  { path: 'books', component: BookPanelViewComponent },
  { path: 'authors', component: AuthorPanelViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
