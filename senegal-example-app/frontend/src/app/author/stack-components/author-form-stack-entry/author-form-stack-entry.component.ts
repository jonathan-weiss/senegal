import {Component, EventEmitter, Input, Output} from '@angular/core';
import {LockableStackEntry} from "../../../shared/component-stack/lockable-stack-entry";
import {AuthorTO} from "../../api/author-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";

@Component({
  selector: 'author-form-stack-entry',
  templateUrl: './author-form-stack-entry.component.html',
  styleUrls: ['./author-form-stack-entry.component.scss'],
})
export class AuthorFormStackEntryComponent extends LockableStackEntry{

  @Input() author: AuthorTO | undefined;
  @Input() stackKey!: StackKey

  @Output() saveClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

}
