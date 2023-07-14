import {Component, OnInit} from '@angular/core';
import {BookService} from "../book.service";
import {CollectionsUtil} from "../../commons/collections.util";
import {BookTO} from "../api/book-to.model";
import {UpdateBookInstructionTO} from "../api/update-book-instruction.to";
import {CreateBookInstructionTO} from "../api/create-book-instruction.to";
import {DeleteBookInstructionTO} from "../api/delete-book-instruction.to";
import {UuidUtil} from "../../commons/uuid.util";


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
      mainAuthorId: entry.mainAuthor.authorId,
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
      bookName: '',
      mainAuthorId: UuidUtil.createFromString(''),
    }
  }

  resetEdit() {
    this.updateBookInstruction = undefined;
    this.createBookInstruction = undefined;
  }
}
