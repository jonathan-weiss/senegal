import {Component, Input, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {filter, Observable, startWith, switchMap} from "rxjs";
import {AuthorTO} from "../../../../author/api/author-to.model";
import {ComponentStackService} from "../../../../shared/component-stack/component-stack.service";
import {
  AuthorFormStackEntryComponent
} from "../../../../author/stack-components/author-form-stack-entry/author-form-stack-entry.component";
import {
  AuthorSearchStackEntryComponent
} from "../../../../author/stack-components/author-search-stack-entry/author-search-stack-entry.component";
import {BookAuthorDescriptionTO} from "../../../api/book-author-description-to.model";
import {BookService} from "../../../book.service";
import {StackKey} from "../../../../shared/component-stack/stack-key";


@Component({
  selector: 'main-author-form-field',
  templateUrl: './main-author-form-field.component.html',
  styleUrls: ['./main-author-form-field.component.scss'],
})
export class MainAuthorFormFieldComponent implements OnInit {

  @Input() mainAuthorFormControl!: FormControl;

  @Input() mainAuthor: BookAuthorDescriptionTO | undefined;
  @Input() fixedField: boolean = false;

  @Input() isLocked!: boolean;
  @Input() stackKey!: StackKey

  authorsOptions!: Observable<ReadonlyArray<BookAuthorDescriptionTO>>

  constructor(private readonly bookService: BookService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit() {
    this.mainAuthorFormControl.setValidators(Validators.required);
    this.mainAuthorFormControl.patchValue(this.mainAuthor)

    if(this.fixedField) {
      this.mainAuthorFormControl.disable();
    }

    this.authorsOptions = this.mainAuthorFormControl.valueChanges.pipe(
      startWith(''),
      filter(searchTerm => typeof searchTerm === "string"),
      switchMap(searchValue => this.bookService.getAllAuthorsFiltered(this.cleanupSearchTerm(searchValue))),
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


  hasMainAuthor(): boolean {
    return this.mainAuthorFormControl.value != undefined;
  }

  onNewAuthor(): void {
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.author = undefined;
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    });
  }

  onEditAuthor(): void {
    const entry: AuthorTO = this.mainAuthorFormControl.value as AuthorTO;
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.author = entry;
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    })
  }

  onSelectAuthor(): void {
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorSearchStackEntryComponent, (component: AuthorSearchStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.showSelectButton = true;
      component.showCancelButton= true;
      component.showDeleteButton = false;
      component.showAddButton = false;
      component.showEditButton = false;
      component.selectClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    })
  }

  private refreshAuthorAfterEditing(entry: AuthorTO | undefined = undefined): void {
    if(entry != undefined) {
      this.mainAuthorFormControl.patchValue(entry);
    }
  }
}
