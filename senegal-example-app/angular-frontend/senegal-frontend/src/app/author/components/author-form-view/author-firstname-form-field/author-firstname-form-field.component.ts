import {Component, Input, OnInit} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {BookSearchViewComponent} from '../../../../book/components/book-search-view/book-search-view.component';
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
export class AuthorFirstnameFormFieldComponent implements OnInit {

  @Input() authorFirstnameFormControl!: FormControl;

  @Input() firstname: string | undefined;

  @Input() isLocked!: boolean;

  ngOnInit() {
    this.authorFirstnameFormControl.setValidators(Validators.required);
    this.authorFirstnameFormControl.patchValue(this.firstname == undefined ? '': this.firstname)
  }
}
