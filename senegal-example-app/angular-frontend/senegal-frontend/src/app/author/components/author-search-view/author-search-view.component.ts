import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {AuthorService} from "../../author.service";
import {ComponentStackService} from "../../../shared/component-stack/component-stack.service";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";
import {
  AuthorFormStackEntryComponent
} from "../../stack-components/author-form-stack-entry/author-form-stack-entry.component";
import {SearchAuthorInstructionTO} from "../../api/search-author-instruction-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {ErrorMessage} from "../../../shared/error-list/error-message.model";
import {ErrorTransformationService} from "../../../shared/error-list/error-transformation.service";
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {AuthorIdFormFieldComponent} from '../author-form-view/author-id-form-field/author-id-form-field.component';
import {
  AuthorFirstnameFormFieldComponent
} from '../author-form-view/author-firstname-form-field/author-firstname-form-field.component';
import {
  AuthorLastnameFormFieldComponent
} from '../author-form-view/author-lastname-form-field/author-lastname-form-field.component';
import {AllBookByAuthorComponent} from '../author-form-view/author-all-book-by-author/all-book-by-author.component';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {AuthorTableViewComponent} from '../author-table-view/author-table-view.component';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'author-search-view',
  templateUrl: './author-search-view.component.html',
  styleUrls: ['./author-search-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatTabsModule,
    AuthorTableViewComponent,
    ErrorListComponent,
    MatButtonModule,
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

  allAuthor: ReadonlyArray<AuthorTO> = []

  highlightedAuthor: AuthorTO | undefined = undefined;

  errorMessages: Array<ErrorMessage> = []


  constructor(private authorService: AuthorService,
              private componentStackService: ComponentStackService,
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
    this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }

  cancel(): void {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }

  add(): void {
    this.highlightedAuthor = undefined;
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.author = undefined;
      component.saveClicked.subscribe((author) => this.reloadAllAuthorsAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllAuthorsAfterEditing());
    });
  }

  edit(entry: AuthorTO): void {
    this.highlightedAuthor = entry;
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.author = entry;
      component.saveClicked.subscribe((author) => this.reloadAllAuthorsAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllAuthorsAfterEditing());
    })
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
