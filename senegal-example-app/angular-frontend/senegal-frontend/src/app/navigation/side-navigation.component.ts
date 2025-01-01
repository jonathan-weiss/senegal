import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
// import {generatedEntitiesNavigationEntries, NavigationEntry} from "../../generated/generated-entities-routing.module";


@Component({
  selector: 'side-navigation-component',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss'],
  standalone: true,
  imports: [
    RouterLink
  ]
})
export class SideNavigationComponent {

  // sideNavigationEntries: ReadonlyArray<NavigationEntry> = generatedEntitiesNavigationEntries;


}
