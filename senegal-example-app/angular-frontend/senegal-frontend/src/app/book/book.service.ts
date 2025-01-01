import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BookTO} from "./api/book-to.model";
import {UpdateBookInstructionTO} from "./api/update-book-instruction.to";
import {CreateBookInstructionTO} from "./api/create-book-instruction.to";
import {DeleteBookInstructionTO} from "./api/delete-book-instruction.to";
import {SearchBookInstructionTO} from "./api/search-book-instruction.to";
import {BookAuthorDescriptionTO} from "./api/book-author-description-to.model";
import {BookInMemoryApiService} from "./api/book-in-memory-api.service";


@Injectable({
  providedIn: 'root',
})
export class BookService {

  constructor(private readonly bookApiService: BookInMemoryApiService) {
  }

  getAllBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.bookApiService.getBooks();
  }

  searchBooks(searchCriteria: SearchBookInstructionTO): Observable<ReadonlyArray<BookTO>> {
    return this.bookApiService.searchAllBook(searchCriteria);
  }

  getAllAuthorsFiltered(searchTerm: string): Observable<ReadonlyArray<BookAuthorDescriptionTO>> {
    return this.bookApiService.getAllAuthorFiltered(searchTerm);
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
