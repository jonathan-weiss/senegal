import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookTO} from "../../api/book-to.model";


@Component({
    selector: 'book-table-view',
    templateUrl: './book-table-view.component.html',
    styleUrls: ['./book-table-view.component.scss'],
})
export class BookTableViewComponent {
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() tableControlsDisabled!: boolean;
  @Input() isLocked!: boolean;


    @Input() allBooks!: ReadonlyArray<BookTO>
    @Input() highlightedBook: BookTO | undefined = undefined;

    @Output() selectEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
    @Output() editEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
    @Output() deleteEntryClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();

    displayedColumns: string[] = [
        'bookId',
        'bookName',
        'mainAuthor',
        'context'
    ];

    asBook(entry: any): BookTO {
      return entry as BookTO
    }

  isHighlighted(book: BookTO): boolean {
      return this.highlightedBook != undefined && book.bookId.uuid == this.highlightedBook.bookId.uuid;
  }

  onRowDoubleClicked(entry: BookTO): void {
      if(this.isLocked) {
        return;
      }

      if(this.showSelectButton) {
        this.selectEntryClicked.emit(entry);
      } else if(this.showEditButton) {
        this.editEntryClicked.emit(entry);
      }
  }


  editClicked(entry: BookTO): void {
    if(this.isLocked) {
      return;
    }
        this.editEntryClicked.emit(entry);
    }

    selectClicked(entry: BookTO): void {
      if(this.isLocked) {
        return;
      }
        this.selectEntryClicked.emit(entry);
    }

    deleteClicked(entry: BookTO): void {
      if(this.isLocked) {
        return;
      }
        this.deleteEntryClicked.emit(entry);
    }

}
