import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {UuidTO} from "../../../../uuid-to.model";


@Component({
  selector: 'author-id-form-field',
  templateUrl: './author-id-form-field.component.html',
  styleUrls: ['./author-id-form-field.component.scss'],
})
export class AuthorIdFormFieldComponent implements OnInit {

  @Input() authorIdFormControl!: FormControl;

  @Input() authorId: UuidTO | undefined;

  ngOnInit() {
    this.authorIdFormControl.disable() // id is not editable
    this.authorIdFormControl.patchValue(this.authorId == undefined ? undefined : this.authorId.uuid)
  }
}
