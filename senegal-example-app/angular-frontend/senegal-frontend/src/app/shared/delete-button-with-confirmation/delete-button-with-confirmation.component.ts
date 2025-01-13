import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatSuffix} from '@angular/material/form-field';
import {MatButtonToggle, MatButtonToggleGroup} from '@angular/material/button-toggle';
import {MatExpansionPanel} from '@angular/material/expansion';


@Component({
  selector: 'delete-button-with-confirmation',
  templateUrl: './delete-button-with-confirmation.component.html',
  styleUrls: ['./delete-button-with-confirmation.component.scss'],
  standalone: true,
  imports: [
    MatIconModule,
    MatButtonModule,
    MatButtonToggleGroup,
    MatButtonToggle,
  ]
})
export class DeleteButtonWithConfirmationComponent {
  @Input() disabled: boolean = false;
  @Input() checked: boolean = false;
  @Output() clickedWithConfirmation: EventEmitter<void> = new EventEmitter<void>();

  @ViewChild("cancelButtonToggle")
  cancelButtonToggle!: MatButtonToggle

  @ViewChild("deleteButtonToggle")
  deleteButtonToggle!: MatButtonToggle

  cancelClicked(): void {
    this.checked = false
  }

  deleteClicked(): void {
    if(this.checked) {
      this.clickedWithConfirmation.emit()
      this.checked = false
    } else {
      this.checked = true
    }
  }

  private resetToggles(): void {
    this.cancelButtonToggle.checked = false
    this.deleteButtonToggle.checked = false
  }

}
