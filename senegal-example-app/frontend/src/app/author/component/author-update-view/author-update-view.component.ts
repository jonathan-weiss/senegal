import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EditableAuthorData} from "../author-edit-view/editable-author.model";
import {AuthorTO} from "../../api/author-to.model";
import {UpdateAuthorInstructionTO} from "../../api/update-author-instruction-to.model";
import {AuthorApiService} from "../../api/author-api.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorService} from "../../author.service";


@Component({
  selector: 'author-update-view-stack-component',
  templateUrl: './author-update-view.component.html',
  styleUrls: ['./author-update-view.component.scss'],
})
export class AuthorUpdateViewComponent {
  @Input() author!: AuthorTO

  @Output() saveClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


  constructor(private authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }

  onCancel() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  onPerformCreateOnServer(authorData: EditableAuthorData): void {
    const updateInstruction: UpdateAuthorInstructionTO = {
      authorId: this.author.authorId,
      firstname: authorData.firstname,
      lastname: authorData.lastname!,
    }
    this.authorService.updateAuthor(updateInstruction).subscribe((author) => {
      this.saveClicked.emit(author);
      this.componentStackService.removeLatestComponentFromStack();
    })
  }

}
