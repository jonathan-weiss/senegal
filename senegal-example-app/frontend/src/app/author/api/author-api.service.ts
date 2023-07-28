import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { Observable }       from 'rxjs';
import { UuidTO } from '../../uuid-to.model';

import { AuthorTO } from "./author-to.model";
import { CreateAuthorInstructionTO } from "./create-author-instruction-to.model";
import { UpdateAuthorInstructionTO } from "./update-author-instruction-to.model";
import { DeleteAuthorInstructionTO } from "./delete-author-instruction-to.model";

@Injectable({
providedIn: 'root'
})
export class AuthorApiService {

    constructor(private readonly httpClient: HttpClient) {}

    getAuthorById(authorId: UuidTO): Observable<AuthorTO> {
        return this.httpClient.get<AuthorTO>(`/api/author/entry/` + authorId.uuid);
    }

    getAllAuthor(): Observable<ReadonlyArray<AuthorTO>> {
        return this.httpClient.get<Array<AuthorTO>>(`/api/author/all`);
    }

    getAllAuthorFiltered(searchTerm: string): Observable<ReadonlyArray<AuthorTO>> {
        return this.httpClient.get<Array<AuthorTO>>(`/api/author/all-filtered?searchTerm=` + searchTerm);
    }

    createAuthor(createInstruction: CreateAuthorInstructionTO): Observable<AuthorTO> {
        return this.httpClient.post<AuthorTO>(`/api/author/entry`, createInstruction);
    }

    updateAuthor(updateInstruction: UpdateAuthorInstructionTO): Observable<AuthorTO> {
        return this.httpClient.put<AuthorTO>(`/api/author/entry`, updateInstruction);
    }

    deleteAuthor(deleteInstruction: DeleteAuthorInstructionTO): Observable<void> {
        return this.httpClient.post<void>(`/api/author/entry/delete`, deleteInstruction);
    }

}
