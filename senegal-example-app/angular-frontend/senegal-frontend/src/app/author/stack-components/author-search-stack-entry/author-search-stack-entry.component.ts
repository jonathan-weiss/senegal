import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LockableStackEntry} from "../../../shared/component-stack/lockable-stack-entry";
import {AuthorTO} from "../../api/author-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {DisplayComponentStackComponent} from '../../../shared/component-stack/display-component-stack.component';
import {AuthorSearchViewComponent} from '../../components/author-search-view/author-search-view.component';

@Component({
  selector: 'author-search-stack-entry',
  templateUrl: './author-search-stack-entry.component.html',
  styleUrls: ['./author-search-stack-entry.component.scss'],
  standalone: true,
  imports: [
    AuthorSearchViewComponent
  ]
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
