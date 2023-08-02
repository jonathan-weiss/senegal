import {Component, Input, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";


@Component({
  selector: 'author-firstname-form-field',
  templateUrl: './author-firstname-form-field.component.html',
  styleUrls: ['./author-firstname-form-field.component.scss'],
})
export class AuthorFirstnameFormFieldComponent implements OnInit {

  @Input() authorFirstnameFormControl!: FormControl;

  @Input() firstname: string | undefined;

  ngOnInit() {
    this.authorFirstnameFormControl.setValidators(Validators.required);
    this.authorFirstnameFormControl.patchValue(this.firstname == undefined ? '': this.firstname)
  }
}
