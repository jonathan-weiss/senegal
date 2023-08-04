import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LockableStackEntry} from "../../../component-stack/lockable-stack-entry";
import {AuthorTO} from "../../api/author-to.model";
import {StackKey} from "../../../component-stack/stack-key";

@Component({
  selector: 'author-search-stack-entry',
  templateUrl: './author-search-stack-entry.component.html',
  styleUrls: ['./author-search-stack-entry.component.scss'],
})
export class AuthorSearchStackEntryComponent extends LockableStackEntry{

  @Input() showCancelButton: boolean = false
  @Input() showAddButton: boolean = false
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false

  @Input() stackKey!: StackKey

  @Output() selectClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
}
