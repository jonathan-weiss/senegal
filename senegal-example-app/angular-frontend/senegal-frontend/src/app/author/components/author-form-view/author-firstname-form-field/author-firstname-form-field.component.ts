import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {DisableFormControlDirective} from '../../../../reactive-forms/disable-form-control.directive';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'author-firstname-form-field',
  templateUrl: './author-firstname-form-field.component.html',
  styleUrls: ['./author-firstname-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    DisableFormControlDirective,
    MatInputModule,
  ]

})
export class AuthorFirstnameFormFieldComponent {

  @Input() authorFirstnameFormControl!: FormControl;

  @Input() isLocked!: boolean;

}
