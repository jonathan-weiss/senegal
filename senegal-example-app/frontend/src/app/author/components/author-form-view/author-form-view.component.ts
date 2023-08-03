import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorFormService} from "./author-form.service";
import {AuthorTO} from "../../api/author-to.model";


@Component({
  selector: 'author-form-view',
  templateUrl: './author-form-view.component.html',
  styleUrls: ['./author-form-view.component.scss'],
})
export class AuthorFormViewComponent implements OnInit {

  @Input() author: AuthorTO | undefined;

  @Output() saveClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  @Input() isLocked!: boolean;

  authorForm: FormGroup = new FormGroup({});

  tabCommonsSelected: EventEmitter<void> = new EventEmitter<void>()
  tabBooksSelected: EventEmitter<void> = new EventEmitter<void>()

  constructor(private authorFormService: AuthorFormService,
              private componentStackService: ComponentStackService,
              ) {
  }

  ngOnInit() {
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

  saveForm(): void {
    if(this.author == undefined) {
      this.authorFormService.performCreateOnServer(this.authorForm).subscribe(book => this.afterServerResponse(book));
    } else {
      this.authorFormService.performUpdateOnServer(this.authorForm, this.author).subscribe(book => this.afterServerResponse(book));
    }

    this.saveClicked.emit();
  }

  private afterServerResponse(author: AuthorTO) {
      this.saveClicked.emit(author);
      this.componentStackService.removeLatestComponentFromStack();
  }

  cancelForm() {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
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
