import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {AuthorTO} from "./api/author-to.model";
import {AuthorApiService} from "./api/author-api.service";
import {UpdateAuthorInstructionTO} from "./api/update-author-instruction-to.model";
import {CreateAuthorInstructionTO} from "./api/create-author-instruction-to.model";
import {DeleteAuthorInstructionTO} from "./api/delete-author-instruction-to.model";
import {UuidTO} from "../uuid-to.model";
import {BookTO} from "../book/api/book-to.model";
import {BookApiService} from "../book/api/book-api.service";
import {SearchAuthorInstructionTO} from "./api/search-author-instruction-to.model";
import {AuthorIdTO} from "./api/author-id-to.model";


@Injectable({
  providedIn: 'root',
})
export class AuthorService {

  constructor(private readonly authorApiService: AuthorApiService,
              private readonly bookApiService: BookApiService) {
  }

  getAllAuthors(): Observable<ReadonlyArray<AuthorTO>> {
    return this.authorApiService.getAllAuthor();
  }

  searchAllAuthor(searchCriteria: SearchAuthorInstructionTO): Observable<ReadonlyArray<AuthorTO>> {
    return this.authorApiService.searchAllAuthor(searchCriteria).pipe(
      map( (entities) => entities)
    );
  }


  getAllBooksByAuthor(authorId: AuthorIdTO): Observable<ReadonlyArray<BookTO>> {
    return this.bookApiService.getAllBookByAuthor(authorId);
  }



  updateAuthor(updateInstruction: UpdateAuthorInstructionTO): Observable<AuthorTO> {
    return this.authorApiService.updateAuthor(updateInstruction);
  }

  createAuthor(createInstruction: CreateAuthorInstructionTO): Observable<AuthorTO> {
    return this.authorApiService.createAuthor(createInstruction);
  }

  deleteAuthor(deleteInstruction: DeleteAuthorInstructionTO): Observable<void> {
    return this.authorApiService.deleteAuthor(deleteInstruction);
  }
}
