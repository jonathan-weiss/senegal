import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookService} from "../book.service";
import {CollectionsUtil} from "../../commons/collections.util";
import {BookTO} from "../../../generated-openapi";


@Component({
  selector: 'book-table-view',
  templateUrl: './book-table-view.component.html',
  styleUrls: ['./book-table-view.component.scss'],
})
export class BookTableViewComponent {

  @Input() books!: ReadonlyArray<BookTO>
  @Input() tableControlsDisabled!: boolean;

  @Output() editEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() deleteEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();

  displayedColumns: string[] = ['bookId', 'bookName', 'context'];

  constructor(private bookService: BookService) {
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
