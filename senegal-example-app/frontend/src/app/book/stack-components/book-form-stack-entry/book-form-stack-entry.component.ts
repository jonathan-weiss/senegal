import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LockableStackEntry} from "../../../shared/component-stack/lockable-stack-entry";
import {AuthorTO} from "../../../author/api/author-to.model";
import {BookTO} from "../../api/book-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";

@Component({
  selector: 'book-form-stack-entry',
  templateUrl: './book-form-stack-entry.component.html',
  styleUrls: ['./book-form-stack-entry.component.scss'],
})
export class BookFormStackEntryComponent extends LockableStackEntry{

  @Input() book: BookTO | undefined;
  @Input() fixedMainAuthor: AuthorTO | undefined = undefined;

  @Input() stackKey!: StackKey

  @Output() saveClicked: EventEmitter<BookTO> = new EventEmitter<BookTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

}
