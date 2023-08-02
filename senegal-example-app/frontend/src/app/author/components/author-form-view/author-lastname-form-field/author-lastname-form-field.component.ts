import {Component, Input, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";


@Component({
  selector: 'author-lastname-form-field',
  templateUrl: './author-lastname-form-field.component.html',
  styleUrls: ['./author-lastname-form-field.component.scss'],
})
export class AuthorLastnameFormFieldComponent implements OnInit {

  @Input() authorLastnameFormControl!: FormControl;

  @Input() lastname: string | undefined;

  ngOnInit() {
    this.authorLastnameFormControl.setValidators(Validators.required);
    this.authorLastnameFormControl.patchValue(this.lastname == undefined ? '': this.lastname)
  }
}
