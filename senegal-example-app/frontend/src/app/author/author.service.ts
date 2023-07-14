import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
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
}
