import {Component, EventEmitter, Input, OnInit} from '@angular/core';
import {AuthorTO} from "../../../api/author-to.model";
import {StackKey} from "../../../../shared/component-stack/stack-key";


@Component({
  selector: 'all-book-by-author',
  templateUrl: './all-book-by-author.component.html',
  styleUrls: ['./all-book-by-author.component.scss'],
})
export class AllBookByAuthorComponent {

  @Input() author: AuthorTO | undefined;
  @Input() reloadAllBookEvent: EventEmitter<void> | undefined = undefined;
  @Input() isLocked!: boolean;
  @Input() stackKey!: StackKey

}
