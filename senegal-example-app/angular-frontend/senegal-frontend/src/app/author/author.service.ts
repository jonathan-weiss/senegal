import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {AuthorTO} from "./api/author-to.model";
import {UpdateAuthorInstructionTO} from "./api/update-author-instruction-to.model";
import {CreateAuthorInstructionTO} from "./api/create-author-instruction-to.model";
import {DeleteAuthorInstructionTO} from "./api/delete-author-instruction-to.model";
import {BookTO} from "../book/api/book-to.model";
import {SearchAuthorInstructionTO} from "./api/search-author-instruction-to.model";
import {AuthorIdTO} from "./api/author-id-to.model";
import {AuthorInMemoryApiService} from "./api/author-in-memory-api.service";
import {BookInMemoryApiService} from "../book/api/book-in-memory-api.service";


@Injectable({
  providedIn: 'root',
})
export class AuthorService {

  constructor(private readonly authorApiService: AuthorInMemoryApiService,
              private readonly bookApiService: BookInMemoryApiService) {
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
