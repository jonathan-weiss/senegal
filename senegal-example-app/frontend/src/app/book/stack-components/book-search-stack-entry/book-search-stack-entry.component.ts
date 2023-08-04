import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {LockableStackEntry} from "../../../component-stack/lockable-stack-entry";
import {AuthorTO} from "../../../author/api/author-to.model";
import {BookTO} from "../../api/book-to.model";
import {StackKey} from "../../../component-stack/stack-key";

@Component({
  selector: 'book-search-stack-entry',
  templateUrl: './book-search-stack-entry.component.html',
  styleUrls: ['./book-search-stack-entry.component.scss'],
})
export class BookSearchStackEntryComponent extends LockableStackEntry{

  @Input() showCancelButton: boolean = false
  @Input() showAddButton: boolean = false
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false

  @Input() stackKey!: StackKey

  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;
  @Input() searchEvent: EventEmitter<void> | undefined = undefined;

  @Output() selectClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
}
