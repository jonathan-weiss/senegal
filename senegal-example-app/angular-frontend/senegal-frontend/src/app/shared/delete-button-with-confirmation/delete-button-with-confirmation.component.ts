import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatSuffix} from '@angular/material/form-field';


@Component({
  selector: 'delete-button-with-confirmation',
  templateUrl: './delete-button-with-confirmation.component.html',
  styleUrls: ['./delete-button-with-confirmation.component.scss'],
  standalone: true,
  imports: [
    MatIconModule,
    MatButtonModule,
    MatSuffix,
  ]
})
export class DeleteButtonWithConfirmationComponent {
  @Input() disabled: boolean = false;
  @Input() checked: boolean = false;
  @Output() clickedWithConfirmation: EventEmitter<void> = new EventEmitter<void>();

  unconfirmedClicked(): void {
      this.checked = true
  }

  cancelClicked(): void {
    this.checked = false
  }

  confirmedClicked(): void {
    this.clickedWithConfirmation.emit()
    this.checked = false
  }

}
