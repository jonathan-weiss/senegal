import {Component, EventEmitter, Input, Output} from '@angular/core';
import {UpdateBookInstructionTO} from "../api/update-book-instruction.to";


@Component({
  selector: 'book-edit-view',
  templateUrl: './book-edit-view.component.html',
  styleUrls: ['./book-edit-view.component.scss'],
})
export class BookEditViewComponent {

  @Input() updateBookInstruction!: UpdateBookInstructionTO


  @Output() saveClicked: EventEmitter<UpdateBookInstructionTO> = new EventEmitter<UpdateBookInstructionTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


  saveChanges(): void {
    this.saveClicked.emit(this.updateBookInstruction);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }
}
