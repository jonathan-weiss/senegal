import { Routes } from '@angular/router';
import {AuthorEntryPointComponent} from './author/components/author-entry-point/author-entry-point.component';
import {CountryEntryPointComponent} from './country/components/country-entry-point/country-entry-point.component';
import {AddressFormComponent} from './address-form/address-form.component';
import {
  SideNavigationAndContentComponent
} from './navigation/side-navigation-and-content/side-navigation-and-content.component';

export const routes: Routes = [

  { path: 'entities',
    component: SideNavigationAndContentComponent,
    children: [
      { path: 'authors', component: AuthorEntryPointComponent },
      { path: 'countries', component: CountryEntryPointComponent },
      { path: 'address-form', component: AddressFormComponent },
    ]
  },
];

