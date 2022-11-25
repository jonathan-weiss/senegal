import {Component, OnInit} from '@angular/core';
import {BookService} from "../book.service";
import {CollectionsUtil} from "../../commons/collections.util";
import {
  BookTO,
  CreateBookInstructionTO,
  DeleteBookInstructionTO,
  UpdateBookInstructionTO
} from "../../../generated-openapi";


@Component({
  selector: 'book-panel-view',
  templateUrl: './book-panel-view.component.html',
  styleUrls: ['./book-panel-view.component.scss'],
})
export class BookPanelViewComponent implements OnInit {

  books: ReadonlyArray<BookTO> = CollectionsUtil.emptyList()

  updateBookInstruction: UpdateBookInstructionTO | undefined = undefined;
  createBookInstruction: CreateBookInstructionTO | undefined = undefined;

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

  isEditingMode(): boolean {
    return this.createBookInstruction != undefined || this.updateBookInstruction != undefined;
  }

  onEdit(entry: BookTO): void {
    this.createBookInstruction = undefined;
    this.updateBookInstruction = {
      bookId: entry.bookId,
      bookName: entry.bookName,
    }
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
      this.updateBookInstruction = undefined;
      this.createBookInstruction = undefined;
    })
  }

  onPerformCreate(createInstruction: CreateBookInstructionTO): void {
    this.bookService.createBook(createInstruction).subscribe(() => {
      this.loadBooks();
      this.updateBookInstruction = undefined;
      this.createBookInstruction = undefined;
    })
  }

  onNewEntry(): void {
    this.updateBookInstruction = undefined;
    this.createBookInstruction = {
      bookName: ''
    }
  }

  resetEdit() {
    this.updateBookInstruction = undefined;
    this.createBookInstruction = undefined;
  }
}
