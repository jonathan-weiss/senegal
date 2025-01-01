import {Component, Input} from '@angular/core';
import {ErrorMessage} from "./error-message.model";
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'error-list',
  templateUrl: './error-list.component.html',
  styleUrls: ['./error-list.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatListModule,
    MatIconModule,
  ]

})
export class ErrorListComponent {

  @Input() errorMessages: Array<ErrorMessage> = []

  hasErrors(): boolean {
    return this.errorMessages.length > 0;
  }

  removeMessage(index: number): void {
    this.errorMessages.splice(index, 1);
  }
}
