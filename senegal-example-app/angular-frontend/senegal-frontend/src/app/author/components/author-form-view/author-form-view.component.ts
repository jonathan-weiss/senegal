import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatTabChangeEvent, MatTabsModule} from "@angular/material/tabs";
import {AuthorFormService} from "./author-form.service";
import {AuthorTO} from "../../api/author-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {ErrorMessage} from "../../../shared/error-list/error-message.model";
import {ErrorTransformationService} from "../../../shared/error-list/error-transformation.service";
import {Observer} from "rxjs";
import {MatCardModule} from '@angular/material/card';
import {AuthorIdFormFieldComponent} from './author-id-form-field/author-id-form-field.component';
import {AuthorFirstnameFormFieldComponent} from './author-firstname-form-field/author-firstname-form-field.component';
import {AuthorLastnameFormFieldComponent} from './author-lastname-form-field/author-lastname-form-field.component';
import {AllBookByAuthorComponent} from './author-all-book-by-author/all-book-by-author.component';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'author-form-view',
  templateUrl: './author-form-view.component.html',
  styleUrls: ['./author-form-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatTabsModule,
    AuthorIdFormFieldComponent,
    AuthorFirstnameFormFieldComponent,
    AuthorLastnameFormFieldComponent,
    AllBookByAuthorComponent,
    ErrorListComponent,
    MatButtonModule,
  ]
})
export class AuthorFormViewComponent implements OnInit {

  @Input() author: AuthorTO | undefined;

  @Output() saveClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  @Input() isLocked!: boolean;
  @Input() stackKey!: StackKey

  authorForm: FormGroup = new FormGroup({});

  tabCommonsSelected: EventEmitter<void> = new EventEmitter<void>()
  tabBooksSelected: EventEmitter<void> = new EventEmitter<void>()

  errorMessages: Array<ErrorMessage> = []

  constructor(private authorFormService: AuthorFormService,
              private errorTransformationService: ErrorTransformationService,
              ) {
  }

  ngOnInit() {
    this.createNewFormGroup()
  }

  private createNewFormGroup(): void {
    this.authorForm = new FormGroup({});
    this.authorFormService.initForm(this.authorForm)
  }

  get authorIdFormControl(): FormControl {
    return this.authorFormService.getFormControl(this.authorForm, this.authorFormService.authorIdFormControlName);
  };
  get authorFirstnameFormControl(): FormControl {
    return this.authorFormService.getFormControl(this.authorForm, this.authorFormService.firstnameFormControlName);
  };
  get authorLastnameFormControl(): FormControl {
    return this.authorFormService.getFormControl(this.authorForm, this.authorFormService.lastnameFormControlName);
  };

  isCreateMode(): boolean {
    return this.author == undefined;
  }

  private storeAuthorObserver: Partial<Observer<AuthorTO>> = {
    next: author => this.afterSuccessfulServerResponse(author),
    error: error => this.errorCase(this.author, error)
  }

  saveForm(): void {
    if(this.author == undefined) {
      this.authorFormService.performCreateOnServer(this.authorForm).subscribe(this.storeAuthorObserver);
    } else {
      this.authorFormService.performUpdateOnServer(this.authorForm, this.author).subscribe(this.storeAuthorObserver);
    }
  }

  private errorCase(entry: AuthorTO | undefined, error: any): void {
    const errorMessage = this.errorTransformationService.transformErrorToMessage(this.entityDescription(entry), error)

    if(errorMessage != undefined) {
      this.errorMessages.push(errorMessage)
    }
  }

  private entityDescription(entry: AuthorTO | undefined): string {
    if(entry == undefined) {
      return 'The Author could not be created.'
    } else {
      return 'The Author ' + entry.firstname + ' ' + entry.lastname + ' could not be stored.'
    }
  }


  private afterSuccessfulServerResponse(author: AuthorTO) {
    this.saveClicked.emit(author);
  }

  cancelForm() {
    this.cancelClicked.emit();
  }


  isFormValid() {
    return !this.authorForm.invalid;
  }

  openTab(tabChangeEvent: MatTabChangeEvent): void {
    switch (tabChangeEvent.index) {
      case 0: this.tabCommonsSelected.emit(); break;
      case 1: this.tabBooksSelected.emit(); break;
    }
  }
}
