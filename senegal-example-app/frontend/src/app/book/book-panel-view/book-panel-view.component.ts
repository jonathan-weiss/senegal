import {Component, OnInit} from '@angular/core';
import {BookService} from "../book.service";
import {CollectionsUtil} from "../../commons/collections.util";
import {BookTO} from "../api/book-to.model";
import {UpdateBookInstructionTO} from "../api/update-book-instruction.to";
import {CreateBookInstructionTO} from "../api/create-book-instruction.to";
import {DeleteBookInstructionTO} from "../api/delete-book-instruction.to";
import {UuidUtil} from "../../commons/uuid.util";
import {EditingModeEnum} from "../../editing-mode.enum";


@Component({
  selector: 'book-panel-view',
  templateUrl: './book-panel-view.component.html',
  styleUrls: ['./book-panel-view.component.scss'],
})
export class BookPanelViewComponent implements OnInit {

  books: ReadonlyArray<BookTO> = CollectionsUtil.emptyList()

  editingMode: EditingModeEnum = EditingModeEnum.NONE
  selectedBook: BookTO | undefined = undefined;

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.loadBooks();
  }

  private loadBooks(): void {
    this.bookService.getAllBooks().subscribe((books: ReadonlyArray<BookTO>) => {
      this.books = books;
    })
  }

  isUpdateMode(): boolean {
    return this.editingMode === EditingModeEnum.UPDATE
  }

  isCreateMode(): boolean {
    return this.editingMode === EditingModeEnum.CREATE
  }

  isInEditingMode(): boolean {
    return this.isUpdateMode() || this.isCreateMode()
  }

  onEdit(entry: BookTO): void {
    this.editingMode = EditingModeEnum.UPDATE;
    this.selectedBook = entry;
  }

  onPerformDelete(entry: BookTO): void {
    const deleteInstruction: DeleteBookInstructionTO = {
      bookId: entry.bookId,
    }
    this.bookService.deleteBook(deleteInstruction).subscribe(() => {
      this.loadBooks();
    })
  }

  onPerformUpdate(updateInstruction: UpdateBookInstructionTO): void {
    this.bookService.updateBook(updateInstruction).subscribe(() => {
      this.loadBooks();
      this.editingMode = EditingModeEnum.NONE;
    })
  }

  onPerformCreate(createInstruction: CreateBookInstructionTO): void {
    this.bookService.createBook(createInstruction).subscribe(() => {
      this.loadBooks();
      this.editingMode = EditingModeEnum.NONE;
    })
  }

  onNewEntry(): void {
    this.editingMode = EditingModeEnum.CREATE;
  }

  resetEdit() {
    this.editingMode = EditingModeEnum.NONE;
  }

  protected readonly undefined = undefined;
}
