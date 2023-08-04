import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BookTO} from "../../api/book-to.model";
import {BookService} from "../../book.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {DeleteBookInstructionTO} from "../../api/delete-book-instruction.to";
import {AuthorTO} from "../../../author/api/author-to.model";
import {
  BookFormStackEntryComponent
} from "../../stack-components/book-form-stack-entry/book-form-stack-entry.component";
import {SearchBookInstructionTO} from "../../api/search-book-instruction.to";
import {bookStackKey} from "../../stack-components/book-stack-key";
import {StackKey} from "../../../component-stack/stack-key";


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
  @Input() stackKey!: StackKey

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
    const searchCriteria: SearchBookInstructionTO = {
      bookId: undefined,
      bookName: undefined,
      mainAuthorId: this.fixedMainAuthor?.authorId,

    }
    this.bookService
      .searchBooks(searchCriteria)
      .subscribe((entities: ReadonlyArray<BookTO>) => {
          this.allBooks = entities;
      });
  }

  select(book: BookTO): void {
    this.selectClicked.emit(book);
    this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }

  cancel(): void {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }

  add(): void {
    this.highlightedBook = undefined;
    this.componentStackService.newComponentOnStack(this.stackKey,BookFormStackEntryComponent, (component: BookFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.book = undefined;
      component.fixedMainAuthor = this.fixedMainAuthor;
      component.saveClicked.subscribe((author) => this.reloadAllBooksAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllBooksAfterEditing());
    });
  }

  edit(entry: BookTO): void {
    this.highlightedBook = entry;
    this.componentStackService.newComponentOnStack(this.stackKey, BookFormStackEntryComponent, (component: BookFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
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
