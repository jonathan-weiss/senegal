import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthorTO} from "./api/author-to.model";
import {AuthorApiService} from "./api/author-api.service";
import {UpdateAuthorInstructionTO} from "./api/update-author-instruction-to.model";
import {CreateAuthorInstructionTO} from "./api/create-author-instruction-to.model";
import {DeleteAuthorInstructionTO} from "./api/delete-author-instruction-to.model";


@Injectable({
  providedIn: 'root',
})
export class AuthorService {

  constructor(private readonly authorApiService: AuthorApiService) {
  }

  getAllAuthors(): Observable<ReadonlyArray<AuthorTO>> {
    return this.authorApiService.getAllAuthor();
  }

  getAllAuthorsFiltered(searchTerm: string): Observable<ReadonlyArray<AuthorTO>> {
    return this.authorApiService.getAllAuthorFiltered(searchTerm);
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
