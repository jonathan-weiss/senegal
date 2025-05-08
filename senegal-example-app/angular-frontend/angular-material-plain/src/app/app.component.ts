import {Component} from '@angular/core';
import {MyNavigationComponent} from './my-navigation/my-navigation.component';

@Component({
  selector: 'app-root',
  imports: [MyNavigationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angular-material-plain';
}
