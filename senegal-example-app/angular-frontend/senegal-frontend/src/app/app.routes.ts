import { Routes } from '@angular/router';
import {BookEntryPointComponent} from './book/stack-components/book-entry-point/book-entry-point.component';
import {AuthorEntryPointComponent} from './author/components/author-entry-point/author-entry-point.component';

export const routes: Routes = [
  { path: 'books', component: BookEntryPointComponent },
  { path: 'authors', component: AuthorEntryPointComponent },
];

