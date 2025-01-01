import {Component, Input, OnInit} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'author-lastname-form-field',
  templateUrl: './author-lastname-form-field.component.html',
  styleUrls: ['./author-lastname-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    DisableFormControlDirective,
    MatInputModule,
  ]

})
export class AuthorLastnameFormFieldComponent implements OnInit {

  @Input() authorLastnameFormControl!: FormControl;

  @Input() lastname: string | undefined;

  @Input() isLocked!: boolean;

  ngOnInit() {
    this.authorLastnameFormControl.setValidators(Validators.required);
    this.authorLastnameFormControl.patchValue(this.lastname == undefined ? '': this.lastname)
  }
}
