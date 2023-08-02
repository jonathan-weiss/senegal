import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {BookApiService} from "./api/book-api.service";
import {BookTO} from "./api/book-to.model";
import {UpdateBookInstructionTO} from "./api/update-book-instruction.to";
import {CreateBookInstructionTO} from "./api/create-book-instruction.to";
import {DeleteBookInstructionTO} from "./api/delete-book-instruction.to";
import {UuidTO} from "../uuid-to.model";
import {BookSearchCriteria} from "./api/book-search-criteria.model";


@Injectable({
  providedIn: 'root',
})
export class BookService {

  constructor(private readonly bookApiService: BookApiService) {
  }

  getAllBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.bookApiService.getBooks();
  }

  getBooks(searchCriteria: BookSearchCriteria): Observable<ReadonlyArray<BookTO>> {
    // TODO implement that in sql
    return this.bookApiService.getBooks().pipe(
      map( (entities) => entities.filter((book: BookTO) => searchCriteria.mainAuthorId == undefined || (book.mainAuthor.authorId.uuid == searchCriteria.mainAuthorId.uuid)))
    );
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
