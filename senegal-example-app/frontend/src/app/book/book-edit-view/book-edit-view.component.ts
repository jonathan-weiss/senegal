import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UpdateBookInstructionTO} from "../api/update-book-instruction.to";
import {BookTO} from "../api/book-to.model";
import {FormControl} from "@angular/forms";
import {AuthorTO} from "../../author/api/author-to.model";
import {CollectionsUtil} from "../../commons/collections.util";
import {AuthorService} from "../../author/author.service";
import {Observable, startWith, switchMap} from "rxjs";


@Component({
  selector: 'book-edit-view',
  templateUrl: './book-edit-view.component.html',
  styleUrls: ['./book-edit-view.component.scss'],
})
export class BookEditViewComponent implements OnInit {

  @Input() book!: BookTO

  @Output() saveClicked: EventEmitter<UpdateBookInstructionTO> = new EventEmitter<UpdateBookInstructionTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  authorsOptions!: Observable<ReadonlyArray<AuthorTO>>

  bookNameFormControl = new FormControl('');
  mainAuthorFormControl = new FormControl('');

  constructor(private authorService: AuthorService) {
  }
  ngOnInit(): void {
    this.bookNameFormControl.patchValue(this.book.bookName)
    this.mainAuthorFormControl.patchValue(this.book.mainAuthor)

    this.authorsOptions = this.mainAuthorFormControl.valueChanges.pipe(
      startWith(''),
      switchMap(searchValue => this.authorService.getAllAuthorsFiltered(searchValue || '')),
    );
  }


  displayAuthorFn(author: AuthorTO): string {
    return author ? author.firstname + ' ' + author.lastname : '';
  }

  saveChanges(): void {
    const updateInstruction: UpdateBookInstructionTO = {
      bookId: this.book.bookId,
      bookName: this.bookNameFormControl.value as string,
      mainAuthorId: (this.mainAuthorFormControl.value as AuthorTO).authorId
    }

    this.saveClicked.emit(updateInstruction);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }
}
