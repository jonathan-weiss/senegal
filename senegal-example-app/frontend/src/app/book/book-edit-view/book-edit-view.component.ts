import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import { Validators } from '@angular/forms';
import {MatTabChangeEvent} from "@angular/material/tabs";
import {EditableBookData} from "./editable-book.model";
import {BookTO} from "../api/book-to.model";
import {AuthorTO} from "../../author/api/author-to.model";
import {filter, Observable, startWith, switchMap} from "rxjs";
import {AuthorService} from "../../author/author.service";
import {ComponentStackService} from "../../component-stack/component-stack.service";
import {AuthorCreateViewComponent} from "../../author/component/author-create-view/author-create-view.component";
import {AuthorUpdateViewComponent} from "../../author/component/author-update-view/author-update-view.component";
import {AuthorSelectViewComponent} from "../../author/component/author-select-view/author-select-view.component";


@Component({
  selector: 'book-edit-view',
  templateUrl: './book-edit-view.component.html',
  styleUrls: ['./book-edit-view.component.scss'],
})
export class BookEditViewComponent implements OnInit {

  @Input() book: BookTO | undefined;
  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;

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

  constructor(private readonly authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit() {
    this.bookIdFormControl.disable();
    if(this.book != undefined) {
      this.bookIdFormControl.patchValue(this.book.bookId.uuid)
      this.bookNameFormControl.patchValue(this.book.bookName)
      this.mainBookAuthorFormControl.patchValue(this.book.mainAuthor)
    }

    if(this.fixedMainAuthor != undefined) {
      this.mainBookAuthorFormControl.patchValue(this.fixedMainAuthor);
      this.mainBookAuthorFormControl.disable();
    }

    this.authorsOptions = this.mainBookAuthorFormControl.valueChanges.pipe(
      startWith(''),
      filter(searchTerm => typeof searchTerm === "string"),
      switchMap(searchValue => this.authorService.getAllAuthorsFiltered(this.cleanupSearchTerm(searchValue))),
    );
  }

  private cleanupSearchTerm(searchValue: string | undefined): string {
    if(searchValue == undefined) {
      return ''
    }
    return searchValue;
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

  hasMainAuthor(): boolean {
    return this.mainBookAuthorFormControl.value != undefined;
  }

  onNewAuthor(): void {
    this.componentStackService.newComponentOnStack(AuthorCreateViewComponent, (component: AuthorCreateViewComponent) => {
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    });
  }

  onEditAuthor(): void {
    const entry: AuthorTO = this.mainBookAuthorFormControl.value as AuthorTO;
    this.componentStackService.newComponentOnStack(AuthorUpdateViewComponent, (component: AuthorUpdateViewComponent) => {
      component.author = entry;
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    })
  }

  onSelectAuthor(): void {
    this.componentStackService.newComponentOnStack(AuthorSelectViewComponent, (component: AuthorSelectViewComponent) => {
      component.selectClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    })
  }

  private refreshAuthorAfterEditing(entry: AuthorTO | undefined = undefined): void {
    if(entry != undefined) {
      this.mainBookAuthorFormControl.patchValue(entry);
    }
  }
}
