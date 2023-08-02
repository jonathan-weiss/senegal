import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BookService} from "../../book.service";
import {UuidTO} from "../../../uuid-to.model";
import {AuthorTO} from "../../../author/api/author-to.model";
import {FormControl, FormGroup} from "@angular/forms";
import {BookTO} from "../../api/book-to.model";
import {CreateBookInstructionTO} from "../../api/create-book-instruction.to";
import {UpdateBookInstructionTO} from "../../api/update-book-instruction.to";


@Injectable({
  providedIn: 'root',
})
export class BookFormService {

  constructor(private readonly bookService: BookService) {
  }

  bookIdFormControlName: string = "bookId";
  bookNameFormControlName: string = "bookName"
  mainAuthorFormControlName: string = "mainAuthor"

  initForm(bookForm: FormGroup): void {
    bookForm.addControl(this.bookIdFormControlName, new FormControl());
    bookForm.addControl(this.bookNameFormControlName, new FormControl());
    bookForm.addControl(this.mainAuthorFormControlName, new FormControl());
  }

  getFormControl(bookForm: FormGroup, formControlName: string): FormControl {
    const control = bookForm.get(formControlName);
    if(control == undefined) {
      throw new Error("No control with name '"+formControlName+"' found in book form " + bookForm)
    }
    return control as FormControl;
  }

  private getBookNameFormValue(bookForm: FormGroup): string {
    return this.getFormControl(bookForm, this.bookNameFormControlName).value as string
  }
  private getMainAuthorIdFormValue(bookForm: FormGroup): UuidTO {
    const mainAuthor: AuthorTO = this.getFormControl(bookForm, this.mainAuthorFormControlName).value as AuthorTO
    return mainAuthor.authorId
  }


  performCreateOnServer(bookForm: FormGroup): Observable<BookTO> {
    const createInstruction: CreateBookInstructionTO = {
      bookName: this.getBookNameFormValue(bookForm),
      mainAuthorId: this.getMainAuthorIdFormValue(bookForm),
    }

    return this.bookService.createBook(createInstruction);
  }

  performUpdateOnServer(bookForm: FormGroup, book: BookTO): Observable<BookTO> {
    const updateInstruction: UpdateBookInstructionTO = {
      bookId: book.bookId,
      bookName: this.getBookNameFormValue(bookForm),
      mainAuthorId: this.getMainAuthorIdFormValue(bookForm),
    }
    return this.bookService.updateBook(updateInstruction);
  }

}
