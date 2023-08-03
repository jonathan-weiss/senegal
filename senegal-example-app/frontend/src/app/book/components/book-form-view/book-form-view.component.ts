import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {BookTO} from "../../api/book-to.model";
import {AuthorTO} from "../../../author/api/author-to.model";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {BookFormService} from "./book-form.service";
import {UuidTO} from "../../../uuid-to.model";
import {BookIdTO} from "../../api/book-id-to.model";
import {BookAuthorDescriptionTO} from "../../api/book-author-description-to.model";


@Component({
  selector: 'book-form-view',
  templateUrl: './book-form-view.component.html',
  styleUrls: ['./book-form-view.component.scss'],
})
export class BookFormViewComponent implements OnInit {

  @Input() book: BookTO | undefined;
  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;
  @Input() isLocked!: boolean

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
      this.componentStackService.removeLatestComponentFromStack();
  }

  cancelForm() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }


  isFormValid() {
    return !this.bookForm.invalid;
  }

  openTab(tabChangeEvent: MatTabChangeEvent): void {
    // nothing to do
  }
}
