import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {AuthorTO} from "./author-to.model";

@Injectable({
  providedIn: 'root',
})
export class AuthorApiService {

  constructor(private readonly httpClient: HttpClient) {
  }

  getAuthors(): Observable<ReadonlyArray<AuthorTO>> {
    return this.httpClient.get<Array<AuthorTO>>(`/api/authors/all`);
  }


}
