import {Component, ViewChild} from '@angular/core';
import {AuthorTO} from '../../api/author-to.model';
import {AuthorResultComponent} from '../author-result/author-result.component';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatButton} from '@angular/material/button';
import {SearchAuthorInstructionTO} from '../../api/search-author-instruction-to.model';
import {DeleteAuthorInstructionTO} from '../../api/delete-author-instruction-to.model';
import {ErrorMessage} from '../../../shared/error-list/error-message.model';
import {AuthorService} from '../../author.service';
import {ErrorTransformationService} from '../../../shared/error-list/error-transformation.service';
import {AuthorSearchBoxComponent} from '../author-search-box/author-search-box.component';
import {AuthorFormViewComponent} from '../author-form-view/author-form-view.component';
import {MatExpansionModule, MatExpansionPanel} from '@angular/material/expansion';

@Component({
  selector: "author-entry-point",
  templateUrl: "author-entry-point.component.html",
  styleUrls: ["author-entry-point.component.scss"],
  standalone: true,
  imports: [
    AuthorResultComponent,
    ErrorListComponent,
    MatButton,
    AuthorSearchBoxComponent,
    AuthorFormViewComponent,
    MatExpansionPanel,
    MatExpansionModule,
  ]
})
export class AuthorEntryPointComponent {

  editing: boolean = false
  selectedAuthor: AuthorTO | undefined = undefined;

  @ViewChild("searchPanel")
  searchPanel!: MatExpansionPanel

  @ViewChild("resultPanel")
  resultPanel!: MatExpansionPanel

  @ViewChild("editPanel")
  editPanel!: MatExpansionPanel


  isEditingMode(): boolean {
    return this.editing
  }

  allAuthor: ReadonlyArray<AuthorTO> = []

  highlightedAuthor: AuthorTO | undefined = undefined;

  errorMessages: Array<ErrorMessage> = []


  constructor(private authorService: AuthorService,
              private errorTransformationService: ErrorTransformationService) {
  }

  ngOnInit(): void {
    this.initiallyLoadAllAuthor();
  }

  isSearchAndResultLocked(): boolean {
    return this.editing
  }

  isMainEditingLocked(): boolean {
    return false // TODO implement
  }

  isSearchPerformed(searchCriteria: SearchAuthorInstructionTO): void {
    this.searchAuthors(searchCriteria)
    this.resultPanel.open()
  }

  private initiallyLoadAllAuthor(): void {
    const searchCriteria: SearchAuthorInstructionTO = {
      authorId: undefined,
      firstname: undefined,
      lastname: undefined,
    }
    this.searchAuthors(searchCriteria)
  }

  private searchAuthors(searchCriteria: SearchAuthorInstructionTO): void {
    this.authorService
      .searchAllAuthor(searchCriteria)
      .subscribe((entities: ReadonlyArray<AuthorTO>) => {
        this.allAuthor = entities;
      });
  }

  deleteEntry(entry: AuthorTO): void {
    this.onPerformDeleteOnServer(entry);
  }

  addNewEntry(): void {
    this.changeSelectedAuthor(undefined, true)
  }

  editEntry(entry: AuthorTO): void {
    this.changeSelectedAuthor(entry, true)
  }

  saveEditedEntry(): void {
    this.changeSelectedAuthor(undefined, false)
    this.editPanel.close()
    this.resultPanel.open()
    // TODO implement
  }

  cancelEditing(): void {
    this.changeSelectedAuthor(undefined, false)
    this.editPanel.close()
    this.resultPanel.open()
    // TODO implement
  }

  private onPerformDeleteOnServer(entry: AuthorTO): void {
    const deleteInstruction: DeleteAuthorInstructionTO = {
      authorId: entry.authorId,
    }
    this.authorService.deleteAuthor(deleteInstruction).subscribe({
      next: (): void => {
        this.reloadAllAuthorsAfterEditing();
      },
      error: (error: any) => this.errorCase(entry, error)
    })
  }

  private errorCase(entry: AuthorTO, error: any): void {
    const entityDescription = 'The Author ' + entry.firstname + ' ' + entry.lastname + ' could not be deleted.'
    const errorMessage = this.errorTransformationService.transformErrorToMessage(entityDescription, error)

    if(errorMessage != undefined) {
      this.errorMessages.push(errorMessage)
    }
  }

  private reloadAllAuthorsAfterEditing(highlightedEntry: AuthorTO | undefined = undefined): void {
    this.initiallyLoadAllAuthor();
    this.highlightedAuthor = highlightedEntry;
  }

  private changeSelectedAuthor(selectedEntry: AuthorTO | undefined, editing: boolean): void {
    this.selectedAuthor = selectedEntry
    this.editing = editing
  }
}
