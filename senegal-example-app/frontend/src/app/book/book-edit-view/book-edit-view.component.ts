import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import { Validators } from '@angular/forms';
import {MatTabChangeEvent} from "@angular/material/tabs";
import {EditableBookData} from "./editable-book.model";
import {BookTO} from "../api/book-to.model";
import {AuthorTO} from "../../author/api/author-to.model";
import {Observable, startWith, switchMap} from "rxjs";
import {AuthorService} from "../../author/author.service";


@Component({
  selector: 'book-edit-view',
  templateUrl: './book-edit-view.component.html',
  styleUrls: ['./book-edit-view.component.scss'],
})
export class BookEditViewComponent implements OnInit {

  @Input() book: BookTO | undefined;

  @Output() saveClicked: EventEmitter<EditableBookData> = new EventEmitter<EditableBookData>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  bookIdFormControl: FormControl =  new FormControl('');
  bookNameFormControl: FormControl = new FormControl('', Validators.required);
  mainBookAuthorFormControl: FormControl = new FormControl('', Validators.required);

  authorsOptions!: Observable<ReadonlyArray<AuthorTO>>

  bookForm = new FormGroup({
    bookId: this.bookIdFormControl,
    bookName: this.bookNameFormControl,
    mainBookAuthor: this.mainBookAuthorFormControl,
  });

  constructor(private readonly authorService: AuthorService) {
  }

  ngOnInit() {
    this.bookIdFormControl.disable();
    if(this.book != undefined) {
      this.bookIdFormControl.patchValue(this.book.bookId.uuid)
      this.bookNameFormControl.patchValue(this.book.bookName)
      this.mainBookAuthorFormControl.patchValue(this.book.mainAuthor)
    }

    this.authorsOptions = this.mainBookAuthorFormControl.valueChanges.pipe(
      startWith(''),
      switchMap(searchValue => this.authorService.getAllAuthorsFiltered(searchValue || '')),
    );

  }

  displayAuthorFn(author: AuthorTO): string {
    return author ? author.firstname + ' ' + author.lastname : '';
  }


  isCreateMode(): boolean {
    return this.book == undefined;
  }

  saveChanges(): void {
    const editable: EditableBookData = {
      bookName: this.bookNameFormControl.value as string,
      mainAuthorId: (this.mainBookAuthorFormControl.value as AuthorTO).authorId.uuid,
    }
    this.saveClicked.emit(editable);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }

  isFormValid() {
    return !this.bookForm.invalid;
  }

  openTab(tabChangeEvent: MatTabChangeEvent): void {
    // nothing to do
  }
}
