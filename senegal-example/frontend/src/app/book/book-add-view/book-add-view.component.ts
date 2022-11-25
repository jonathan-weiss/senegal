import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CreateBookInstructionTO} from "../../../generated-openapi";


@Component({
  selector: 'book-add-view',
  templateUrl: './book-add-view.component.html',
  styleUrls: ['./book-add-view.component.scss'],
})
export class BookAddViewComponent {

  @Input() createBookInstruction!: CreateBookInstructionTO


  @Output() saveClicked: EventEmitter<CreateBookInstructionTO> = new EventEmitter<CreateBookInstructionTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


  saveChanges(): void {
    this.saveClicked.emit(this.createBookInstruction);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }
}
