import {Injectable} from '@angular/core';
import {EMPTY, Observable, of} from 'rxjs';

import {AuthorTO} from "./author-to.model";
import {CreateAuthorInstructionTO} from "./create-author-instruction-to.model";
import {UpdateAuthorInstructionTO} from "./update-author-instruction-to.model";
import {DeleteAuthorInstructionTO} from "./delete-author-instruction-to.model";
import {SearchAuthorInstructionTO} from "./search-author-instruction-to.model";
import {AuthorIdTO} from "./author-id-to.model";
import {UuidUtil} from "../../commons/uuid.util";

@Injectable({
providedIn: 'root'
})
export class AuthorInMemoryApiService {

    private authors: Array<AuthorTO> = [
      {
        authorId: {
          value: "34aa97e7-9c15-434a-81f0-1a2e12066281"
        },
        firstname: "William",
        lastname: "Golding",
      },
      {
        authorId: {
          value: "3d5436c7-1170-4515-b11c-93b99921c26c"
        },
        firstname: "Jonathan",
        lastname: "Swift",
      },
      {
        authorId: {
          value: "a4a16270-bd19-453a-9067-632f300c8cff"
        },
        firstname: "God",
        lastname: "Almighty",
      },
    ];


  constructor() {}

    private findAuthorIndexById(authorId: AuthorIdTO): number {
      const authorIndex: number = this.authors.findIndex(author => author.authorId.value === authorId.value)
      if(authorIndex == -1) {
        throw new Error("Author index not found for " + authorId)
      }
      return authorIndex;
    }

    findAuthorById(authorId: AuthorIdTO): AuthorTO {
      const author = this.authors.find(author => author.authorId.value === authorId.value)
      if(author == null) {
        throw new Error("Book not found for author " + authorId)
      }
      return author
    }

    getAuthorById(authorId: AuthorIdTO): Observable<AuthorTO> {
        return of(this.findAuthorById(authorId))
    }

    getAllAuthor(): Observable<ReadonlyArray<AuthorTO>> {
        return of(this.authors)
    }

    searchAllAuthor(searchCriteria: SearchAuthorInstructionTO): Observable<ReadonlyArray<AuthorTO>> {
      return of(this.authors) // TODO add search criteria
    }

    createAuthor(createInstruction: CreateAuthorInstructionTO): Observable<AuthorTO> {
      const author: AuthorTO = {
          authorId: {
            value: UuidUtil.generateNewUuid().uuid
          },
          firstname: createInstruction.firstname,
          lastname: createInstruction.lastname,
        }
      this.authors.push(author)
      this.refreshAuthors()
      return of(author)
    }

    updateAuthor(updateInstruction: UpdateAuthorInstructionTO): Observable<AuthorTO> {
      const authorIndex: number = this.findAuthorIndexById(updateInstruction.authorId)
      const oldAuthor: AuthorTO = this.findAuthorById(updateInstruction.authorId)
      const newAuthor: AuthorTO = {
        authorId: oldAuthor.authorId,
        firstname: updateInstruction.firstname,
        lastname: updateInstruction.lastname,
      }
      this.authors.splice(authorIndex, 1, newAuthor)
      this.refreshAuthors()
      return of(newAuthor)
    }

    deleteAuthor(deleteInstruction: DeleteAuthorInstructionTO): Observable<void> {
      const authorIndex: number = this.findAuthorIndexById(deleteInstruction.authorId)
      this.authors.splice(authorIndex, 1)
      this.refreshAuthors()
      return of(undefined)
    }

    private refreshAuthors() {
      const authors: Array<AuthorTO> = this.authors
      this.authors = []
      this.authors.push(...authors)
    }

}
