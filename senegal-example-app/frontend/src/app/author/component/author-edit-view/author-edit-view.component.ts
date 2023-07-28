import { Component, Input, Output, EventEmitter } from '@angular/core';
import { UpdateAuthorInstructionTO } from "../../api/update-author-instruction-to.model";


@Component({
    selector: 'author-edit-view',
    templateUrl: './author-edit-view.component.html',
    styleUrls: ['./author-edit-view.component.scss'],
})
export class AuthorEditViewComponent {

    @Input() updateAuthorInstruction!: UpdateAuthorInstructionTO

    @Output() saveClicked: EventEmitter<UpdateAuthorInstructionTO> = new EventEmitter<UpdateAuthorInstructionTO>();
    @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


    saveChanges(): void {
        this.saveClicked.emit(this.updateAuthorInstruction);
    }

    cancelEdit(): void {
        this.cancelClicked.emit();
    }
}
