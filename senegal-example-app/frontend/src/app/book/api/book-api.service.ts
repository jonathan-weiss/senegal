import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {BookTO} from "./book-to.model";
import {CreateBookInstructionTO} from "./create-book-instruction.to";
import {UpdateBookInstructionTO} from "./update-book-instruction.to";
import {DeleteBookInstructionTO} from "./delete-book-instruction.to";
import {UuidTO} from "../../uuid-to.model";


@Injectable({
  providedIn: 'root',
})
export class BookApiService {

  constructor(private readonly httpClient: HttpClient) {
  }

  getBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all`);
  }

  getBookById(bookId: UuidTO): Observable<BookTO> {
    return this.httpClient.get<BookTO>(`/api/books/entry/` + bookId.uuid);
  }

  getAllBook(): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all`);
  }

  createBook(createInstruction: CreateBookInstructionTO): Observable<BookTO> {
    return this.httpClient.post<BookTO>(`/api/books/entry`, createInstruction);
  }

  updateBook(updateInstruction: UpdateBookInstructionTO): Observable<BookTO> {
    return this.httpClient.put<BookTO>(`/api/books/entry`, updateInstruction);
  }

  deleteBook(deleteInstruction: DeleteBookInstructionTO): Observable<void> {
    return this.httpClient.post<void>(`/api/books/entry/delete`, deleteInstruction);
  }


}
