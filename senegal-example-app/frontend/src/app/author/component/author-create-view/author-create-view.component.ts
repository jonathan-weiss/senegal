import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EditableAuthorData} from "../author-edit-view/editable-author.model";
import {CreateAuthorInstructionTO} from "../../api/create-author-instruction-to.model";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorService} from "../../author.service";
import {CreatePersonInstructionTO} from "../../../../generated/person/api/create-person-instruction-to.model";
import {AuthorTO} from "../../api/author-to.model";


@Component({
  selector: 'author-create-view',
  templateUrl: './author-create-view.component.html',
  styleUrls: ['./author-create-view.component.scss'],
})
export class AuthorCreateViewComponent {

  @Output() saveClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  constructor(private authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }


  onCancel() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  onPerformCreateOnServer(authorData: EditableAuthorData) {
    const createInstruction: CreateAuthorInstructionTO = {
      firstname: authorData.firstname,
      lastname: authorData.lastname,
    }

    this.authorService.createAuthor(createInstruction).subscribe((author) => {
      this.saveClicked.emit(author);
      this.componentStackService.removeLatestComponentFromStack();
    })

  }


}
