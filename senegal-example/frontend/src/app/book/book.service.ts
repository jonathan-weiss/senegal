import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  BookApiService,
  BookTO,
  CreateBookInstructionTO,
  DeleteBookInstructionTO,
  UpdateBookInstructionTO
} from "../../generated-openapi";


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
