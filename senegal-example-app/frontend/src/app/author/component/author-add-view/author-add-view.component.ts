import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CreateAuthorInstructionTO } from "../../api/create-author-instruction-to.model";


@Component({
    selector: 'author-add-view',
    templateUrl: './author-add-view.component.html',
    styleUrls: ['./author-add-view.component.scss'],
})
export class AuthorAddViewComponent {

    @Input() createAuthorInstruction!: CreateAuthorInstructionTO


    @Output() saveClicked: EventEmitter<CreateAuthorInstructionTO> = new EventEmitter<CreateAuthorInstructionTO>();
    @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


    saveChanges(): void {
        this.saveClicked.emit(this.createAuthorInstruction);
    }

    cancelEdit(): void {
        this.cancelClicked.emit();
    }
}
