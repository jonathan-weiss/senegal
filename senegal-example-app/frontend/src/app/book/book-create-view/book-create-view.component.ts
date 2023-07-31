import {Component, EventEmitter, Output} from '@angular/core';
import {BookTO} from "../api/book-to.model";
import {BookService} from "../book.service";
import {CreateBookInstructionTO} from "../api/create-book-instruction.to";
import {ComponentStackService} from "../../component-stack/component-stack.service";
import {EditableBookData} from "../book-edit-view/editable-book.model";
import {UuidUtil} from "../../commons/uuid.util";


@Component({
  selector: 'book-create-view',
  templateUrl: './book-create-view.component.html',
  styleUrls: ['./book-create-view.component.scss'],
})
export class BookCreateViewComponent {

  @Output() saveClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  constructor(private bookService: BookService,
              private componentStackService: ComponentStackService) {
  }


  onCancel() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }

  onPerformCreateOnServer(bookData: EditableBookData) {
    const createInstruction: CreateBookInstructionTO = {
      bookName: bookData.bookName,
      mainAuthorId: UuidUtil.createFromString(bookData.mainAuthorId),
    }

    this.bookService.createBook(createInstruction).subscribe((book) => {
      this.saveClicked.emit(book);
      this.componentStackService.removeLatestComponentFromStack();
    })

  }


}
