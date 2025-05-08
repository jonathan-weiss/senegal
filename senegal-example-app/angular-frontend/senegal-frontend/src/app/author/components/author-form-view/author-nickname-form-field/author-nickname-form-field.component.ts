import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {NullableFieldComponent} from '../../../../shared/nullable-field/nullable-field.component';


@Component({
  selector: 'author-nickname-form-field',
  templateUrl: './author-nickname-form-field.component.html',
  styleUrls: ['./author-nickname-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    NullableFieldComponent,
  ]

})
export class AuthorNicknameFormFieldComponent {

  @Input() authorNicknameFormControl!: FormControl;

  @Input() authorNicknameIsNullFormControl!: FormControl;


  @Input() isLocked!: boolean;

  onNicknameIsNullChange(isNicknameNull: boolean) {
    console.log("isNicknameNull", isNicknameNull);
    // TODO deactivate the validator for the nickname field
  }
}
