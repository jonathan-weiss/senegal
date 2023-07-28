import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import {EditableAuthorData} from "./editable-author.model";
import {FormControl, FormGroup} from "@angular/forms";
import {AuthorTO} from "../../api/author-to.model";
import { Validators } from '@angular/forms';


@Component({
  selector: 'author-edit-view',
  templateUrl: './author-edit-view.component.html',
  styleUrls: ['./author-edit-view.component.scss'],
})
export class AuthorEditViewComponent implements OnInit {

  @Input() author: AuthorTO | undefined;

  @Output() saveClicked: EventEmitter<EditableAuthorData> = new EventEmitter<EditableAuthorData>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  private authorIdFormControl: FormControl =  new FormControl('');
  firstnameFormControl: FormControl = new FormControl('', Validators.required);
  lastnameFormControl: FormControl = new FormControl('', Validators.required);

  authorForm = new FormGroup({
    authorId: this.authorIdFormControl,
    firstname: this.firstnameFormControl,
    lastname: this.lastnameFormControl,
  });

  ngOnInit() {
    this.authorIdFormControl.disable();
    if(this.author != undefined) {
      this.authorIdFormControl.patchValue(this.author.authorId.uuid)
      this.firstnameFormControl.patchValue(this.author.firstname)
      this.lastnameFormControl.patchValue(this.author.lastname)
    }
  }

  isCreateMode(): boolean {
    return this.author == undefined;
  }

  saveChanges(): void {
    const editable: EditableAuthorData = {
      firstname: this.firstnameFormControl.value as string,
      lastname: this.lastnameFormControl.value as string,
    }
    this.saveClicked.emit(editable);
  }

  cancelEdit(): void {
    this.cancelClicked.emit();
  }

  isFormValid() {
    return !this.authorForm.invalid;
  }
}
