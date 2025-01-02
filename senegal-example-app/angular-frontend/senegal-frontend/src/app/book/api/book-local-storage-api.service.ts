import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {BookTO} from "./book-to.model";
import {CreateBookInstructionTO} from "./create-book-instruction.to";
import {UpdateBookInstructionTO} from "./update-book-instruction.to";
import {DeleteBookInstructionTO} from "./delete-book-instruction.to";
import {SearchBookInstructionTO} from "./search-book-instruction.to";
import {AuthorIdTO} from "../../author/api/author-id-to.model";
import {BookIdTO} from "./book-id-to.model";
import {AuthorTO} from "../../author/api/author-to.model";
import {BookAuthorDescriptionTO} from "./book-author-description-to.model";
import {UuidUtil} from "../../commons/uuid.util";
import {AuthorLocalStorageApiService} from "../../author/api/author-local-storage-api.service";
import {LocalStorageService} from '../../shared/local-storage.service';


@Injectable({
  providedIn: 'root',
})
export class BookLocalStorageApiService {

  constructor(private authorLocalStorageApiService: AuthorLocalStorageApiService,
              private localStorageService: LocalStorageService) {
  }

  private findBookIndexById(bookId: BookIdTO): number {
    const bookIndex: number = this.getBooksFromLocalStorage().findIndex(book => book.bookId.value === bookId.value)
    if(bookIndex == -1) {
      throw new Error("Book index not found for " + bookId)
    }
    return bookIndex;
  }

  private findBookById(bookId: BookIdTO): BookTO {
    const book = this.getBooksFromLocalStorage().find(book => book.bookId.value === bookId.value)
    if(book == null) {
      throw new Error("Book not found for author " + bookId)
    }
    return book
  }


  getBooks(): Observable<ReadonlyArray<BookTO>> {
    return of(this.getBooksFromLocalStorage())
  }

  getBookById(bookId: BookIdTO): Observable<BookTO> {
    throw new Error("No Books")
  }

  getAllBook(): Observable<ReadonlyArray<BookTO>> {
    return of(this.getBooksFromLocalStorage())
  }

  searchAllBook(searchParams: SearchBookInstructionTO): Observable<ReadonlyArray<BookTO>> {
    return of(this.getBooksFromLocalStorage()) // TODO filter by search params
  }

  getAllAuthorFiltered(searchTerm: string): Observable<ReadonlyArray<BookAuthorDescriptionTO>> {
    return of(this.getBooksFromLocalStorage().map(book => book.mainAuthor)) // TODO filter by search params
  }

  getAllBookByAuthor(authorId: AuthorIdTO): Observable<ReadonlyArray<BookTO>> {
    return of(this.getBooksFromLocalStorage()) // TODO filter by search params
  }

  createBook(createInstruction: CreateBookInstructionTO): Observable<BookTO> {
    const author: AuthorTO = this.authorLocalStorageApiService.findAuthorById(createInstruction.mainAuthorId)
    const book: BookTO = {
      bookId: {
        value: UuidUtil.generateNewUuid().uuid
      },
      bookName: createInstruction.bookName,
      mainAuthor: {
        authorId: author.authorId,
        firstname: author.firstname,
        lastname: author.lastname,
      }
    }
    const books = this.getBooksFromLocalStorage()
    books.push(book)
    this.storeBooksInLocalStorage(books)
    return of(book)
  }

  updateBook(updateInstruction: UpdateBookInstructionTO): Observable<BookTO> {
    const bookIndex: number = this.findBookIndexById(updateInstruction.bookId)
    const oldBook: BookTO = this.findBookById(updateInstruction.bookId)
    const newAuthor: AuthorTO = this.authorLocalStorageApiService.findAuthorById(updateInstruction.mainAuthorId)
    const newBook: BookTO = {
      bookId: oldBook.bookId,
      bookName: updateInstruction.bookName,
      mainAuthor: {
        authorId: newAuthor.authorId,
        firstname: newAuthor.firstname,
        lastname: newAuthor.lastname,
      },
    }
    const books = this.getBooksFromLocalStorage()
    books.splice(bookIndex, 1, newBook)
    this.storeBooksInLocalStorage(books)
    return of(newBook)

  }

  deleteBook(deleteInstruction: DeleteBookInstructionTO): Observable<void> {
    const bookIndex: number = this.findBookIndexById(deleteInstruction.bookId)
    const books = this.getBooksFromLocalStorage()
    books.splice(bookIndex, 1)
    this.storeBooksInLocalStorage(books)
    return of(undefined)

  }

  private initialBooks: Array<BookTO> = [
    {
      bookId: {
        value: "6d9f2fd3-5a31-4477-8d04-bf87f00f5406"
      },
      bookName: "Gullivers traveling guide",
      mainAuthor: {
        authorId: {
          value: "3d5436c7-1170-4515-b11c-93b99921c26c"
        },
        firstname: "Jonathan",
        lastname: "Swift",
      },
    },
    {
      bookId: {
        value: "f16294de-ba92-4643-8e69-53adfe88d003"
      },
      bookName: "Lord of the flies",
      mainAuthor: {
        authorId: {
          value: "34aa97e7-9c15-434a-81f0-1a2e12066281"
        },
        firstname: "William",
        lastname: "Golding",
      },
    },
    {
      bookId: {
        value: "d1d9874b-07d9-4eb4-82d2-123ca6839a5d"
      },
      bookName: "Thomson study bible",
      mainAuthor: {
        authorId: {
          value: "a4a16270-bd19-453a-9067-632f300c8cff"
        },
        firstname: "God",
        lastname: "Almighty",
      },
    },
  ];

  private getBooksFromLocalStorage(): Array<BookTO> {
    return this.localStorageService.getLocalStorageOrStoreDefault("BOOKS", this.initialBooks) as Array<BookTO>
  }

  private storeBooksInLocalStorage(books: Array<BookTO>) {
    this.localStorageService.saveLocalStorage("BOOKS", books)
  }

}
