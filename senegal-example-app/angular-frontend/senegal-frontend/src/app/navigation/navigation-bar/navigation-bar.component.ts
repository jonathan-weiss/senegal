import {Component, inject} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule, MatIconButton} from '@angular/material/button';
import {LocalStorageService} from '../../shared/local-storage.service';

@Component({
  selector: 'navigation-bar',
  imports: [
    RouterModule,
    MatToolbarModule,
    MatIconModule,
    MatIconButton,
    MatButtonModule
  ],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.scss'
})
export class NavigationBarComponent {

  private localStorageService: LocalStorageService = inject(LocalStorageService);

  clearLocalStorage() {
    this.localStorageService.clearLocalStorage();
  }

}
