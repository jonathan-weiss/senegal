import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthorService} from "../../author.service";
import {UpdateAuthorInstructionTO} from "../../api/update-author-instruction-to.model";
import {CreateAuthorInstructionTO} from "../../api/create-author-instruction-to.model";
import {AuthorTO} from "../../api/author-to.model";


@Injectable({
  providedIn: 'root',
})
export class AuthorFormService {

  constructor(private readonly authorService: AuthorService) {
  }

  authorIdFormControlName: string = "authorId";
  firstnameFormControlName: string = "firstname"
  lastnameFormControlName: string = "lastname"

  initForm(authorForm: FormGroup): void {
    authorForm.addControl(this.authorIdFormControlName, new FormControl());
    authorForm.addControl(this.firstnameFormControlName, new FormControl());
    authorForm.addControl(this.lastnameFormControlName, new FormControl());
  }

  getFormControl(authorForm: FormGroup, formControlName: string): FormControl {
    const control = authorForm.get(formControlName);
    if(control == undefined) {
      throw new Error("No control with name '"+formControlName+"' found in author form " + authorForm)
    }
    return control as FormControl;
  }

  private getFirstnameFormValue(bookForm: FormGroup): string {
    return this.getFormControl(bookForm, this.firstnameFormControlName).value as string
  }
  private getLastnameFormValue(bookForm: FormGroup): string {
    return this.getFormControl(bookForm, this.lastnameFormControlName).value as string
  }


  performCreateOnServer(authorForm: FormGroup): Observable<AuthorTO> {
    const createInstruction: CreateAuthorInstructionTO = {
      firstname: this.getFirstnameFormValue(authorForm),
      lastname: this.getLastnameFormValue(authorForm),
    }

    return this.authorService.createAuthor(createInstruction);
  }

  performUpdateOnServer(authorForm: FormGroup, author: AuthorTO): Observable<AuthorTO> {
    const updateInstruction: UpdateAuthorInstructionTO = {
      authorId: author.authorId,
      firstname: this.getFirstnameFormValue(authorForm),
      lastname: this.getLastnameFormValue(authorForm),
    }
    return this.authorService.updateAuthor(updateInstruction);
  }

}
