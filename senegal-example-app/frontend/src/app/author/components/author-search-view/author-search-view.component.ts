import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {AuthorService} from "../../author.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";
import {
  AuthorFormStackEntryComponent
} from "../../stack-components/author-form-stack-entry/author-form-stack-entry.component";
import {SearchAuthorInstrctionTO} from "../../api/search-author-instruction-to.model";


@Component({
  selector: 'author-search-view',
  templateUrl: './author-search-view.component.html',
  styleUrls: ['./author-search-view.component.scss'],
})
export class AuthorSearchViewComponent implements OnInit {
  @Input() showCancelButton: boolean = false
  @Input() showAddButton: boolean = false
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean

  @Output() selectClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  allAuthor: ReadonlyArray<AuthorTO> = []

  highlightedAuthor: AuthorTO | undefined = undefined;


  constructor(private authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.loadAllAuthor();
  }

  private loadAllAuthor(): void {
    const searchCriteria: SearchAuthorInstrctionTO = {
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
    this.componentStackService.removeLatestComponentFromStack();
  }

  cancel(): void {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  add(): void {
    this.highlightedAuthor = undefined;
    this.componentStackService.newComponentOnStack(AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.author = undefined;
      component.saveClicked.subscribe((author) => this.reloadAllAuthorsAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllAuthorsAfterEditing());
    });
  }

  edit(entry: AuthorTO): void {
    this.highlightedAuthor = entry;
    this.componentStackService.newComponentOnStack(AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
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
    this.authorService.deleteAuthor(deleteInstruction).subscribe(() => {
      this.reloadAllAuthorsAfterEditing();
    });
  }

  private reloadAllAuthorsAfterEditing(highlightedEntry: AuthorTO | undefined = undefined): void {
    this.loadAllAuthor();
    this.highlightedAuthor = highlightedEntry;
  }

}
