import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {BookTO} from "./book-to.model";
import {CreateBookInstructionTO} from "./create-book-instruction.to";
import {UpdateBookInstructionTO} from "./update-book-instruction.to";
import {DeleteBookInstructionTO} from "./delete-book-instruction.to";
import {SearchBookInstructionTO} from "./search-book-instruction.to";
import {AuthorIdTO} from "../../author/api/author-id-to.model";
import {BookIdTO} from "./book-id-to.model";
import {AuthorTO} from "../../author/api/author-to.model";
import {BookAuthorDescriptionTO} from "./book-author-description-to.model";


@Injectable({
  providedIn: 'root',
})
export class BookApiService {

  constructor(private readonly httpClient: HttpClient) {
  }

  getBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all`);
  }

  getBookById(bookId: BookIdTO): Observable<BookTO> {
    return this.httpClient.get<BookTO>(`/api/books/entry/` + bookId.value);
  }

  getAllBook(): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all`);
  }

  searchAllBook(searchParams: SearchBookInstructionTO): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.post<Array<BookTO>>(`/api/books/search`, searchParams);
  }

  getAllAuthorFiltered(searchTerm: string): Observable<ReadonlyArray<BookAuthorDescriptionTO>> {
    // TODO use the api under /api/books/...
    return this.httpClient.get<Array<AuthorTO>>(`/api/author/all-filtered?searchTerm=` + searchTerm);
  }

  getAllBookByAuthor(authorId: AuthorIdTO): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all-by-author/` + authorId.value);
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
