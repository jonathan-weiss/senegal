import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UuidTO} from "../../../../uuid-to.model";
import {BookIdTO} from "../../../api/book-id-to.model";
import {MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'book-id-form-field',
  templateUrl: './book-id-form-field.component.html',
  styleUrls: ['./book-id-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ]

})
export class BookIdFormFieldComponent implements OnInit {

  @Input() bookIdFormControl!: FormControl;

  @Input() bookId: BookIdTO | undefined;

  ngOnInit() {
    this.bookIdFormControl.disable() // id is not editable
    this.bookIdFormControl.patchValue(this.bookId == undefined ? undefined : this.bookId.value)
  }
}
