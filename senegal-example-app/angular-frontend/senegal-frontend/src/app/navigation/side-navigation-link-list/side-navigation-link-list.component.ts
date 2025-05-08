import {Component} from '@angular/core';
import {MatListModule} from '@angular/material/list';


@Component({
  selector: 'side-navigation-link-list',
  templateUrl: './side-navigation-link-list.component.html',
  styleUrls: ['./side-navigation-link-list.component.scss'],
  standalone: true,
  imports: [
    MatListModule
  ]
})
export class SideNavigationLinkListComponent {}
