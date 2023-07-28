import {Component, OnInit} from '@angular/core';
import {AuthorApiService} from "../../api/author-api.service";
import {AuthorTO} from "../../api/author-to.model";
import {CreateAuthorInstructionTO} from "../../api/create-author-instruction-to.model";
import {UpdateAuthorInstructionTO} from "../../api/update-author-instruction-to.model";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";
import {EditingModeEnum} from "../../../editing-mode.enum";
import {EditableAuthorData} from "../author-edit-view/editable-author.model";

@Component({
  selector: 'author-panel-view',
  templateUrl: './author-panel-view.component.html',
  styleUrls: ['./author-panel-view.component.scss'],
})
export class AuthorPanelViewComponent implements OnInit {

  allAuthor: ReadonlyArray<AuthorTO> = []

  editingMode: EditingModeEnum = EditingModeEnum.NONE;
  authorToEdit: AuthorTO | undefined = undefined;

  constructor(private authorService: AuthorApiService) {
  }

  ngOnInit(): void {
    this.loadAllAuthor();
  }

  private loadAllAuthor(): void {
    this.authorService
      .getAllAuthor()
      .subscribe((entities: ReadonlyArray<AuthorTO>) => {
        this.allAuthor = entities;
      });
  }

  isEditingMode(): boolean {
    return this.editingMode == EditingModeEnum.CREATE || this.editingMode == EditingModeEnum.UPDATE;
  }

  onNewEntry(): void {
    this.editingMode = EditingModeEnum.CREATE;
  }

  onEdit(entry: AuthorTO): void {
    this.editingMode = EditingModeEnum.UPDATE;
    this.authorToEdit = entry;
  }

  resetEdit() {
    this.editingMode = EditingModeEnum.NONE;
    this.authorToEdit = undefined;
  }


  onPerformDeleteOnServer(entry: AuthorTO): void {
    const deleteInstruction: DeleteAuthorInstructionTO = {
      authorId: entry.authorId,
    }
    this.authorService.deleteAuthor(deleteInstruction).subscribe(() => {
      this.loadAllAuthor();
    });
  }

  onPerformCreateOrUpdateOnServer(authorData: EditableAuthorData): void {
    switch (this.editingMode) {
      case EditingModeEnum.CREATE:
        this.performCreateOnServer(authorData)
        break;
      case EditingModeEnum.UPDATE:
        if(this.authorToEdit != null) {
          this.performUpdateOnServer(this.authorToEdit, authorData);
        } else {
          throw new Error("In Update mode but no Author to edit defined.")
        }
    }
  }

  private performCreateOnServer(authorData: EditableAuthorData): void {
    const createInstruction: CreateAuthorInstructionTO = {
      firstname: authorData.firstname,
      lastname: authorData.lastname,
    }
    this.authorService.createAuthor(createInstruction).subscribe(() => {
      this.reloadAllAuthorsAfterEditing();
    })

  }

  private performUpdateOnServer(author: AuthorTO, authorData: EditableAuthorData): void {
    const updateInstruction: UpdateAuthorInstructionTO = {
      authorId: author.authorId,
      firstname: authorData.firstname,
      lastname: authorData.lastname!,
    }
    this.authorService.updateAuthor(updateInstruction).subscribe(() => {
      this.reloadAllAuthorsAfterEditing();
    })
  }

  private reloadAllAuthorsAfterEditing(): void {
    this.loadAllAuthor();
    this.editingMode = EditingModeEnum.NONE;
    this.authorToEdit = undefined;

  }

}
