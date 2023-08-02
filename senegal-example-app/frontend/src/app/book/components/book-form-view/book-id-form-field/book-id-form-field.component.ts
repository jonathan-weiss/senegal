import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UuidTO} from "../../../../uuid-to.model";


@Component({
  selector: 'book-id-form-field',
  templateUrl: './book-id-form-field.component.html',
  styleUrls: ['./book-id-form-field.component.scss'],
})
export class BookIdFormFieldComponent implements OnInit {

  @Input() bookIdFormControl!: FormControl;

  @Input() bookId: UuidTO | undefined;

  ngOnInit() {
    this.bookIdFormControl.disable() // id is not editable
    this.bookIdFormControl.patchValue(this.bookId == undefined ? undefined : this.bookId.uuid)
  }
}
