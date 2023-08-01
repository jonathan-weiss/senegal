import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BookApiService} from "./api/book-api.service";
import {BookTO} from "./api/book-to.model";
import {UpdateBookInstructionTO} from "./api/update-book-instruction.to";
import {CreateBookInstructionTO} from "./api/create-book-instruction.to";
import {DeleteBookInstructionTO} from "./api/delete-book-instruction.to";
import {UuidTO} from "../uuid-to.model";


@Injectable({
  providedIn: 'root',
})
export class BookService {

  constructor(private readonly bookApiService: BookApiService) {
  }

  getAllBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.bookApiService.getBooks();
  }

  updateBook(updateInstruction: UpdateBookInstructionTO): Observable<BookTO> {
    return this.bookApiService.updateBook(updateInstruction);
  }

  createBook(createInstruction: CreateBookInstructionTO): Observable<BookTO> {
    return this.bookApiService.createBook(createInstruction);
  }

  deleteBook(deleteInstruction: DeleteBookInstructionTO): Observable<void> {
    return this.bookApiService.deleteBook(deleteInstruction);
  }
}
