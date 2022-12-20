import {Component} from '@angular/core';
import {generatedEntitiesNavigationEntries, NavigationEntry} from "../../generated/generated-entities-routing.module";


@Component({
  selector: 'side-navigation-component',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss'],
})
export class SideNavigationComponent {

  sideNavigationEntries: ReadonlyArray<NavigationEntry> = generatedEntitiesNavigationEntries;


}
