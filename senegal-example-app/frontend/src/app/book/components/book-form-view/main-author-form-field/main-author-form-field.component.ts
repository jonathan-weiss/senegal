import {Component, Input, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {filter, Observable, startWith, switchMap} from "rxjs";
import {AuthorTO} from "../../../../author/api/author-to.model";
import {AuthorService} from "../../../../author/author.service";
import {ComponentStackService} from "../../../../component-stack/component-stack.service";
import {
  AuthorFormStackEntryComponent
} from "../../../../author/stack-components/author-form-stack-entry/author-form-stack-entry.component";
import {
  AuthorSearchStackEntryComponent
} from "../../../../author/stack-components/author-search-stack-entry/author-search-stack-entry.component";


@Component({
  selector: 'main-author-form-field',
  templateUrl: './main-author-form-field.component.html',
  styleUrls: ['./main-author-form-field.component.scss'],
})
export class MainAuthorFormFieldComponent implements OnInit {

  @Input() mainAuthorFormControl!: FormControl;

  @Input() mainAuthor: AuthorTO | undefined;
  @Input() fixedField: boolean = false;

  @Input() isLocked!: boolean;

  authorsOptions!: Observable<ReadonlyArray<AuthorTO>>

  constructor(private readonly authorService: AuthorService,
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


  hasMainAuthor(): boolean {
    return this.mainAuthorFormControl.value != undefined;
  }

  onNewAuthor(): void {
    this.componentStackService.newComponentOnStack(AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.author = undefined;
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    });
  }

  onEditAuthor(): void {
    const entry: AuthorTO = this.mainAuthorFormControl.value as AuthorTO;
    this.componentStackService.newComponentOnStack(AuthorFormStackEntryComponent, (component: AuthorFormStackEntryComponent) => {
      component.author = entry;
      component.saveClicked.subscribe((author) => this.refreshAuthorAfterEditing(author));
      component.cancelClicked.subscribe(() => this.refreshAuthorAfterEditing());
    })
  }

  onSelectAuthor(): void {
    this.componentStackService.newComponentOnStack(AuthorSearchStackEntryComponent, (component: AuthorSearchStackEntryComponent) => {
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
