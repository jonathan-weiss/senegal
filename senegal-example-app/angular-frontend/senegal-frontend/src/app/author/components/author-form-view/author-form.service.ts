import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthorService} from "../../author.service";
import {UpdateAuthorInstructionTO} from "../../api/update-author-instruction-to.model";
import {CreateAuthorInstructionTO} from "../../api/create-author-instruction-to.model";
import {AuthorTO} from "../../api/author-to.model";


@Injectable({
  providedIn: 'root',
})
export class AuthorFormService {

  private static formEnableDisableOptions = {
    onlySelf: true,
    emitEvent:false,
  }

  constructor(private readonly authorService: AuthorService) {
  }

  authorIdFormControlName: string = "authorId";
  firstnameFormControlName: string = "firstname"
  nicknameFormControlName: string = "nickname"
  nicknameIsNullFormControlName: string = "nicknameIsNull"
  lastnameFormControlName: string = "lastname"

  initForm(author: AuthorTO | undefined, disabled: Boolean): FormGroup {
    const authorForm: FormGroup = new FormGroup({});
    const authorIdFormControl = new FormControl()
    authorIdFormControl.disable() // id is not editable
    authorIdFormControl.patchValue(author?.authorId?.value ?? undefined)
    authorForm.addControl(this.authorIdFormControlName, authorIdFormControl);

    const authorFirstnameFormControl = new FormControl()
    authorFirstnameFormControl.setValidators(Validators.required);
    authorFirstnameFormControl.patchValue(author?.firstname ?? '')
    authorForm.addControl(this.firstnameFormControlName, authorFirstnameFormControl);

    const authorNicknameFormControl = new FormControl()
    authorNicknameFormControl.setValidators(Validators.required);
    authorNicknameFormControl.patchValue(author?.nickname ?? '')
    authorForm.addControl(this.nicknameFormControlName, authorNicknameFormControl);

    const authorNicknameIsNullFormControl = new FormControl()
    authorNicknameIsNullFormControl.setValidators(Validators.required);
    authorNicknameIsNullFormControl.patchValue(author?.nickname == undefined)
    authorForm.addControl(this.nicknameIsNullFormControlName, authorNicknameIsNullFormControl);

    const authorLastnameFormControl = new FormControl()
    authorLastnameFormControl.setValidators(Validators.required);
    authorLastnameFormControl.patchValue(author?.lastname ?? '')
    authorForm.addControl(this.lastnameFormControlName, authorLastnameFormControl);

    this.updateFormDisableStateDirectly(authorForm, disabled)
    return authorForm
  }

  updateFormDisableState(form: FormGroup, disabled: Boolean): void {
    this.updateFormDisableStateDirectly(form, disabled)
  }

  private updateFormDisableStateDirectly(form: FormGroup, disabled: Boolean): void {
    if(disabled) {
      form.disable(AuthorFormService.formEnableDisableOptions)
    } else {
      form.enable(AuthorFormService.formEnableDisableOptions)
    }
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
  private getNicknameFormValueOrNull(bookForm: FormGroup): string | null {
    if(this.getFormControl(bookForm, this.nicknameIsNullFormControlName).value) {
      return null
    } else {
      return this.getFormControl(bookForm, this.nicknameFormControlName).value as string
    }
  }
  private getLastnameFormValue(bookForm: FormGroup): string {
    return this.getFormControl(bookForm, this.lastnameFormControlName).value as string
  }


  performCreateOnServer(authorForm: FormGroup): Observable<AuthorTO> {
    const createInstruction: CreateAuthorInstructionTO = {
      firstname: this.getFirstnameFormValue(authorForm),
      nickname: this.getNicknameFormValueOrNull(authorForm),
      lastname: this.getLastnameFormValue(authorForm),
    }

    return this.authorService.createAuthor(createInstruction);
  }

  performUpdateOnServer(authorForm: FormGroup, author: AuthorTO): Observable<AuthorTO> {
    const updateInstruction: UpdateAuthorInstructionTO = {
      authorId: author.authorId,
      firstname: this.getFirstnameFormValue(authorForm),
      nickname: this.getNicknameFormValueOrNull(authorForm),
      lastname: this.getLastnameFormValue(authorForm),
    }
    return this.authorService.updateAuthor(updateInstruction);
  }

}
