import {Component} from '@angular/core';
import {MatListModule} from '@angular/material/list';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'side-navigation-link-list',
  templateUrl: './side-navigation-link-list.component.html',
  styleUrls: ['./side-navigation-link-list.component.scss'],
  standalone: true,
  imports: [
    MatListModule,
    RouterModule
  ]
})
export class SideNavigationLinkListComponent {}
