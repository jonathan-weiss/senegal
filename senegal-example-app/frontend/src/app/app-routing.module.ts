import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookEntryPointComponent} from "./book/stack-components/book-entry-point/book-entry-point.component";
import {AuthorEntryPointComponent} from "./author/stack-components/author-entry-point/author-entry-point.component";

const routes: Routes = [
  { path: 'books', component: BookEntryPointComponent },
  { path: 'authors', component: AuthorEntryPointComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
