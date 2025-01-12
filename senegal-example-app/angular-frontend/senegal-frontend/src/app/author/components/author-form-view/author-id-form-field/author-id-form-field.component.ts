import {Component, Input, OnInit} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'author-id-form-field',
  templateUrl: './author-id-form-field.component.html',
  styleUrls: ['./author-id-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ]

})
export class AuthorIdFormFieldComponent {

  @Input() authorIdFormControl!: FormControl;
}
