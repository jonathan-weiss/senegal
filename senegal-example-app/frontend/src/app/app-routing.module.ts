import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthorPanelViewComponent} from "./author/components/author-panel-view/author-panel-view.component";
import {BookPanelViewComponent} from "./book/components/book-panel-view/book-panel-view.component";

const routes: Routes = [
  { path: 'books', component: BookPanelViewComponent },
  { path: 'authors', component: AuthorPanelViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
