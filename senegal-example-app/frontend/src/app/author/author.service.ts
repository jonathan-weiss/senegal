import {Injectable} from '@angular/core';
import {EMPTY, Observable} from 'rxjs';
import {AuthorApiService} from "./api/author-api.service";
import {AuthorTO} from "./api/author-to.model";



@Injectable({
  providedIn: 'root',
})
export class AuthorService {

  constructor(private readonly authorApiService: AuthorApiService) {
  }

  getAllAuthors(): Observable<ReadonlyArray<AuthorTO>> {
    return this.authorApiService.getAuthors();
  }

  getAllAuthorsFiltered(filter: String): Observable<ReadonlyArray<AuthorTO>> {
    if(filter.length < 3) {
      return EMPTY;
    }
    return this.authorApiService.getAuthors();
  }
}
