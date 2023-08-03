import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BookTO} from "../../api/book-to.model";
import {BookService} from "../../book.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {BookFormViewComponent} from "../book-form-view/book-form-view.component";
import {DeleteBookInstructionTO} from "../../api/delete-book-instruction.to";
import {AuthorTO} from "../../../author/api/author-to.model";
import {BookSearchCriteria} from "../../api/book-search-criteria.model";
import {
  BookFormStackEntryComponent
} from "../../stack-components/book-form-stack-entry/book-form-stack-entry.component";


@Component({
  selector: 'book-search-view',
  templateUrl: './book-search-view.component.html',
  styleUrls: ['./book-search-view.component.scss'],
})
export class BookSearchViewComponent implements OnInit {
  @Input() showCancelButton: boolean = false
  @Input() showAddButton: boolean = false
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false

  @Input() isLocked!: boolean;

  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;
  @Input() searchEvent: EventEmitter<void> | undefined = undefined;

  @Output() selectClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  allBooks: ReadonlyArray<BookTO> = []

  highlightedBook: BookTO | undefined = undefined;


  constructor(private bookService: BookService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.loadAllBooks();
    if(this.searchEvent != undefined) {
      this.searchEvent.subscribe(() => this.loadAllBooks());
    }
  }

  private loadAllBooks(): void {
    const searchCriteria: BookSearchCriteria = {
      bookId: undefined,
      bookName: undefined,
      mainAuthorId: this.fixedMainAuthor?.authorId,

    }
    this.bookService
      .getBooks(searchCriteria)
      .subscribe((entities: ReadonlyArray<BookTO>) => {
          this.allBooks = entities;
      });
  }

  select(book: BookTO): void {
    this.selectClicked.emit(book);
    this.componentStackService.removeLatestComponentFromStack();
  }

  cancel(): void {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  add(): void {
    this.highlightedBook = undefined;
    this.componentStackService.newComponentOnStack(BookFormStackEntryComponent, (component: BookFormStackEntryComponent) => {
      component.book = undefined;
      component.fixedMainAuthor = this.fixedMainAuthor;
      component.saveClicked.subscribe((author) => this.reloadAllBooksAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllBooksAfterEditing());
    });
  }

  edit(entry: BookTO): void {
    this.highlightedBook = entry;
    this.componentStackService.newComponentOnStack(BookFormStackEntryComponent, (component: BookFormStackEntryComponent) => {
      component.book = entry;
      component.fixedMainAuthor = this.fixedMainAuthor;
      component.saveClicked.subscribe((author) => this.reloadAllBooksAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllBooksAfterEditing());
    })
  }

  delete(entry: BookTO): void {
    this.onPerformDeleteOnServer(entry);
  }

  private onPerformDeleteOnServer(entry: BookTO): void {
    const deleteInstruction: DeleteBookInstructionTO = {
      bookId: entry.bookId,
    }
    this.bookService.deleteBook(deleteInstruction).subscribe(() => {
      this.reloadAllBooksAfterEditing();
    });
  }

  private reloadAllBooksAfterEditing(highlightedEntry: BookTO | undefined = undefined): void {
    this.loadAllBooks();
    this.highlightedBook = highlightedEntry;
  }

}
