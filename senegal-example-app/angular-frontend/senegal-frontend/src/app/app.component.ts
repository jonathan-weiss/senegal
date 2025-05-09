import { Component } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {
  SideNavigationAndContentComponent
} from './navigation/side-navigation-and-content/side-navigation-and-content.component';
import {NavigationBarComponent} from './navigation/navigation-bar/navigation-bar.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    SideNavigationAndContentComponent,
    NavigationBarComponent,
    RouterOutlet,
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {}
