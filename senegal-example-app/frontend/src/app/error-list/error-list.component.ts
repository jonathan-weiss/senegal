import {Component, Input} from '@angular/core';
import {ErrorMessage} from "./error-message.model";

@Component({
  selector: 'error-list',
  templateUrl: './error-list.component.html',
  styleUrls: ['./error-list.component.scss'],
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
