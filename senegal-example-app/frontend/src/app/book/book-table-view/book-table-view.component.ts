import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookService} from "../book.service";
import {CollectionsUtil} from "../../commons/collections.util";
import {BookTO} from "../api/book-to.model";
import {AuthorTO} from "../../author/api/author-to.model";


@Component({
  selector: 'book-table-view',
  templateUrl: './book-table-view.component.html',
  styleUrls: ['./book-table-view.component.scss'],
})
export class BookTableViewComponent {

  @Input() books!: ReadonlyArray<BookTO>
  @Input() highlightedBook: BookTO | undefined = undefined;
  @Input() tableControlsDisabled!: boolean;

  @Output() editEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() deleteEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();

  displayedColumns: string[] = ['bookId', 'bookName', 'mainAuthor', 'context'];

  constructor(private bookService: BookService) {
  }

  asBook(entry: any): BookTO {
    return entry as BookTO
  }

  isHighlighted(book: BookTO): boolean {
    return this.highlightedBook != undefined && book.bookId.uuid == this.highlightedBook.bookId.uuid;
  }

  getEntries(): BookTO[] {
    return CollectionsUtil.toMutableArray(this.books);
  }

  onCtxEditClicked(entry: BookTO): void {
    this.editEntryClicked.emit(entry);
  }

  onRowDoubleClicked(entry: BookTO): void {
    this.editEntryClicked.emit(entry);
  }

  onCtxDeleteClicked(entry: BookTO): void {
    this.deleteEntryClicked.emit(entry);
  }
}
