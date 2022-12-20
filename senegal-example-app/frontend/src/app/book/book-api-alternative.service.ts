import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  BookApiService,
  BookTO,
  CreateBookInstructionTO,
  DeleteBookInstructionTO,
  UpdateBookInstructionTO
} from "../../generated-openapi";
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root',
})
export class BookApiAlternativeService {

  constructor(private readonly httpClient: HttpClient) {
  }

  getBooks(): Observable<ReadonlyArray<BookTO>> {
    return this.httpClient.get<Array<BookTO>>(`/api/books/all`);
  }
}
