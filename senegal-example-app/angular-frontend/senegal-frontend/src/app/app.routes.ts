import { Routes } from '@angular/router';
import {AuthorEntryPointComponent} from './author/components/author-entry-point/author-entry-point.component';
import {CountryEntryPointComponent} from './country/components/country-entry-point/country-entry-point.component';

export const routes: Routes = [
  { path: 'authors', component: AuthorEntryPointComponent },
  { path: 'countries', component: CountryEntryPointComponent },
];

