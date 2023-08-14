import {Component, EventEmitter, Input, Output} from '@angular/core';
import { AuthorTO } from "../../api/author-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";


@Component({
    selector: 'author-table-view',
    templateUrl: './author-table-view.component.html',
    styleUrls: ['./author-table-view.component.scss'],
})
export class AuthorTableViewComponent {
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean;
  @Input() stackKey!: StackKey


    @Input() allAuthor!: ReadonlyArray<AuthorTO>
    @Input() highlightedAuthor: AuthorTO | undefined = undefined;

    @Output() selectEntryClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
    @Output() editEntryClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
    @Output() deleteEntryClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();

    displayedColumns: string[] = [
        'authorId',
        'firstname',
        'lastname',
        'context'
    ];

    asAuthor(entry: any): AuthorTO {
      return entry as AuthorTO
    }

  isHighlighted(author: AuthorTO): boolean {
      return this.highlightedAuthor != undefined && author.authorId.value == this.highlightedAuthor.authorId.value;
  }

  onRowDoubleClicked(entry: AuthorTO): void {
      if(this.showSelectButton) {
        this.selectEntryClicked.emit(entry);
      } else if(this.showEditButton) {
        this.editEntryClicked.emit(entry);
      }
  }


  editClicked(entry: AuthorTO): void {
        this.editEntryClicked.emit(entry);
    }

    selectClicked(entry: AuthorTO): void {
        this.selectEntryClicked.emit(entry);
    }

    deleteClicked(entry: AuthorTO): void {
        this.deleteEntryClicked.emit(entry);
    }

}
