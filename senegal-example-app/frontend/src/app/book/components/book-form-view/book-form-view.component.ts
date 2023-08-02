import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {BookTO} from "../../api/book-to.model";
import {AuthorTO} from "../../../author/api/author-to.model";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {BookFormService} from "./book-form.service";


@Component({
  selector: 'book-form-view',
  templateUrl: './book-form-view.component.html',
  styleUrls: ['./book-form-view.component.scss'],
})
export class BookFormViewComponent {

  @Input() book: BookTO | undefined;
  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;

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
  };
  get bookNameFormControl(): FormControl {
    return this.bookFormService.getFormControl(this.bookForm, this.bookFormService.bookNameFormControlName);
  };
  get mainAuthorFormControl(): FormControl {
    return this.bookFormService.getFormControl(this.bookForm, this.bookFormService.mainAuthorFormControlName);
  };

  isCreateMode(): boolean {
    return this.book == undefined;
  }

  saveForm(): void {
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
