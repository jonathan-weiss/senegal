import { Component } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {
  SideNavigationAndContentComponent
} from './navigation/side-navigation-and-content/side-navigation-and-content.component';

@Component({
  selector: 'app-root',
  imports: [
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    SideNavigationAndContentComponent,
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {}
