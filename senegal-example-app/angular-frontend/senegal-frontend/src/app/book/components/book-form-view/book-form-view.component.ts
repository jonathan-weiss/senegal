import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatTabChangeEvent, MatTabsModule} from "@angular/material/tabs";
import {BookTO} from "../../api/book-to.model";
import {AuthorTO} from "../../../author/api/author-to.model";
import {ComponentStackService} from "../../../shared/component-stack/component-stack.service";
import {BookFormService} from "./book-form.service";
import {BookIdTO} from "../../api/book-id-to.model";
import {BookAuthorDescriptionTO} from "../../api/book-author-description-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {MatError, MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {BookIdFormFieldComponent} from './book-id-form-field/book-id-form-field.component';
import {BookNameFormFieldComponent} from './book-name-form-field/book-name-form-field.component';
import {MainAuthorFormFieldComponent} from './main-author-form-field/main-author-form-field.component';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'book-form-view',
  templateUrl: './book-form-view.component.html',
  styleUrls: ['./book-form-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatTabsModule,
    BookIdFormFieldComponent,
    BookNameFormFieldComponent,
    MainAuthorFormFieldComponent,
    MatButtonModule,
  ]

})
export class BookFormViewComponent implements OnInit {

  @Input() book: BookTO | undefined;
  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;
  @Input() isLocked!: boolean
  @Input() stackKey!: StackKey

  @Output() saveClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  bookForm: FormGroup = new FormGroup({});

  constructor(private bookFormService: BookFormService,
              private componentStackService: ComponentStackService,
              ) {
  }

  ngOnInit() {
    this.bookFormService.initForm(this.bookForm)
  }

  get bookIdFormControl(): FormControl {
    return this.bookFormService.getFormControl(this.bookForm, this.bookFormService.bookIdFormControlName);
  }
  get bookNameFormControl(): FormControl {
    return this.bookFormService.getFormControl(this.bookForm, this.bookFormService.bookNameFormControlName);
  }
  get mainAuthorFormControl(): FormControl {
    return this.bookFormService.getFormControl(this.bookForm, this.bookFormService.mainAuthorFormControlName);
  }


  bookId(): BookIdTO | undefined {
    return this.book?.bookId;
  }
  bookName(): string | undefined {
    return this.book?.bookName;
  }

  mainAuthor(): BookAuthorDescriptionTO | undefined {
    if(this.fixedMainAuthor != undefined) {
      return this.fixedMainAuthor
    }
    return this.book?.mainAuthor
  }

  isCreateMode(): boolean {
    return this.book == undefined;
  }

  saveForm(): void {
    if(this.isLocked) {
      return;
    }

    if(this.book == undefined) {
      this.bookFormService.performCreateOnServer(this.bookForm).subscribe(book => this.afterServerResponse(book));
    } else {
      this.bookFormService.performUpdateOnServer(this.bookForm, this.book).subscribe(book => this.afterServerResponse(book));
    }

    this.saveClicked.emit();
  }

  private afterServerResponse(book: BookTO) {
      this.saveClicked.emit(book);
      this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }

  cancelForm() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack(this.stackKey);
  }


  isFormValid() {
    return !this.bookForm.invalid;
  }

  openTab(tabChangeEvent: MatTabChangeEvent): void {
    // nothing to do
  }
}
