import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CreateBookInstructionTO} from "../api/create-book-instruction.to";
import {AuthorTO} from "../../author/api/author-to.model";
import {CollectionsUtil} from "../../commons/collections.util";
import {AuthorService} from "../../author/author.service";
import {FormControl} from "@angular/forms";
import {map, Observable, startWith, switchMap} from "rxjs";


@Component({
  selector: 'book-add-view',
  templateUrl: './book-add-view.component.html',
  styleUrls: ['./book-add-view.component.scss'],
})
export class BookAddViewComponent implements OnInit {

  @Output() saveClicked: EventEmitter<CreateBookInstructionTO> = new EventEmitter<CreateBookInstructionTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  authorsOptions!: Observable<ReadonlyArray<AuthorTO>>
  constructor(private authorService: AuthorService) {
  }

  bookNameFormControl = new FormControl('');
  mainAuthorFormControl = new FormControl('');

  ngOnInit(): void {
    this.authorsOptions = this.mainAuthorFormControl.valueChanges.pipe(
      startWith(''),
      switchMap(searchValue => this.authorService.getAllAuthorsFiltered(searchValue || '')),
    );
  }

  displayAuthorFn(author: AuthorTO): string {
    return author ? author.firstname + ' ' + author.lastname : '';
  }


  saveChanges(): void {
    const createInstruction: CreateBookInstructionTO = {
      bookName: this.bookNameFormControl.value as string,
      mainAuthorId: (this.mainAuthorFormControl.value as AuthorTO).authorId
    }

    this.saveClicked.emit(createInstruction);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }

}
