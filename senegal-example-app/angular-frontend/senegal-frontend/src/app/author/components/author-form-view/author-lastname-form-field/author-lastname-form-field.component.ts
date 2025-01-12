import {Component, Input, OnInit} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'author-lastname-form-field',
  templateUrl: './author-lastname-form-field.component.html',
  styleUrls: ['./author-lastname-form-field.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ]

})
export class AuthorLastnameFormFieldComponent {

  @Input() authorLastnameFormControl!: FormControl;

  @Input() isLocked!: boolean;
}
