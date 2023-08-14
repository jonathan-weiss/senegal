import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {AuthorIdTO} from "../../../api/author-id-to.model";


@Component({
  selector: 'author-id-form-field',
  templateUrl: './author-id-form-field.component.html',
  styleUrls: ['./author-id-form-field.component.scss'],
})
export class AuthorIdFormFieldComponent implements OnInit {

  @Input() authorIdFormControl!: FormControl;

  @Input() authorId: AuthorIdTO | undefined;

  ngOnInit() {
    this.authorIdFormControl.disable() // id is not editable
    this.authorIdFormControl.patchValue(this.authorId == undefined ? undefined : this.authorId.value)
  }
}
