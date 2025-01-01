import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'book-name-form-field',
  templateUrl: './book-name-form-field.component.html',
  styleUrls: ['./book-name-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    DisableFormControlDirective,
    MatInputModule,
  ]

})
export class BookNameFormFieldComponent implements OnInit {

  @Input() bookNameFormControl!: FormControl;

  @Input() bookName: string | undefined;

  @Input() isLocked!: boolean;



  ngOnInit() {
    this.bookNameFormControl.setValidators(Validators.required);
    this.bookNameFormControl.patchValue(this.bookName == undefined ? '': this.bookName)
  }
}
