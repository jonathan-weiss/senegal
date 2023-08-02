import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'book-name-form-field',
  templateUrl: './book-name-form-field.component.html',
  styleUrls: ['./book-name-form-field.component.scss'],
})
export class BookNameFormFieldComponent implements OnInit {

  @Input() bookNameFormControl!: FormControl;

  @Input() bookName: string | undefined;

  ngOnInit() {
    this.bookNameFormControl.setValidators(Validators.required);
    this.bookNameFormControl.patchValue(this.bookName == undefined ? '': this.bookName)
  }
}
