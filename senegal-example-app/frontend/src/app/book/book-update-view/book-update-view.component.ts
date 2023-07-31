import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookTO} from "../api/book-to.model";
import {BookService} from "../book.service";
import {ComponentStackService} from "../../component-stack/component-stack.service";
import {EditableBookData} from "../book-edit-view/editable-book.model";
import {UpdateBookInstructionTO} from "../api/update-book-instruction.to";
import {UuidUtil} from "../../commons/uuid.util";


@Component({
  selector: 'book-update-view',
  templateUrl: './book-update-view.component.html',
  styleUrls: ['./book-update-view.component.scss'],
})
export class BookUpdateViewComponent {
  @Input() book!: BookTO

  @Output() saveClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


  constructor(private bookService: BookService,
              private componentStackService: ComponentStackService) {
  }

  onCancel() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  onPerformCreateOnServer(bookData: EditableBookData): void {
    const updateInstruction: UpdateBookInstructionTO = {
      bookId: this.book.bookId,
      bookName: bookData.bookName,
      mainAuthorId: UuidUtil.createFromString(bookData.mainAuthorId),
    }
    this.bookService.updateBook(updateInstruction).subscribe((book) => {
      this.saveClicked.emit(book);
      this.componentStackService.removeLatestComponentFromStack();
    })
  }

}
