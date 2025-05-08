import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {LocalStorageService} from '../shared/local-storage.service';
// import {generatedEntitiesNavigationEntries, NavigationEntry} from "../../generated/generated-entities-routing.module";


@Component({
  selector: 'side-navigation-component',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss'],
  standalone: true,
  imports: [
    RouterLink,
    MatButton
  ]
})
export class SideNavigationComponent {

  constructor(private localStorageService: LocalStorageService) {
  }

  // sideNavigationEntries: ReadonlyArray<NavigationEntry> = generatedEntitiesNavigationEntries;


  clearLocalStorage() {
    this.localStorageService.clearLocalStorage();
  }
}
