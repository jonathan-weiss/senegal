import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {AuthorService} from "../../author.service";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";
import {SearchAuthorInstructionTO} from "../../api/search-author-instruction-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {ErrorMessage} from "../../../shared/error-list/error-message.model";
import {ErrorTransformationService} from "../../../shared/error-list/error-transformation.service";
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatButtonModule} from '@angular/material/button';
import {AuthorResultComponent} from '../author-result/author-result.component';


@Component({
  selector: 'author-search-view',
  templateUrl: './author-search-view.component.html',
  styleUrls: ['./author-search-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatTabsModule,
    AuthorResultComponent,
    ErrorListComponent,
    MatButtonModule,
    AuthorResultComponent,
  ]
})
export class AuthorSearchViewComponent implements OnInit {
  @Input() showCancelButton: boolean = false
  @Input() showAddButton: boolean = false
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean
  @Input() stackKey!: StackKey

  @Output() selectClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
  @Output() addClicked: EventEmitter<void> = new EventEmitter<void>();
  @Output() editClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() deleteClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() searchClicked: EventEmitter<SearchAuthorInstructionTO> = new EventEmitter<SearchAuthorInstructionTO>();

  allAuthor: ReadonlyArray<AuthorTO> = []

  highlightedAuthor: AuthorTO | undefined = undefined;

  errorMessages: Array<ErrorMessage> = []


  constructor(private authorService: AuthorService,
              private errorTransformationService: ErrorTransformationService) {
  }

  ngOnInit(): void {
    this.loadAllAuthor();
  }

  private loadAllAuthor(): void {
    const searchCriteria: SearchAuthorInstructionTO = {
      authorId: undefined,
      firstname: undefined,
      lastname: undefined,
    }

    this.authorService
      .searchAllAuthor(searchCriteria)
      .subscribe((entities: ReadonlyArray<AuthorTO>) => {
        this.allAuthor = entities;
      });
  }

  select(author: AuthorTO): void {
    this.selectClicked.emit(author);
  }

  cancel(): void {
    this.cancelClicked.emit();
  }

  add(): void {
    this.highlightedAuthor = undefined;
    this.addClicked.emit()
  }

  edit(entry: AuthorTO): void {
    this.highlightedAuthor = entry;
    this.editClicked.emit(entry)
  }

  delete(entry: AuthorTO): void {
    this.onPerformDeleteOnServer(entry);
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
    this.loadAllAuthor();
    this.highlightedAuthor = highlightedEntry;
  }

}
