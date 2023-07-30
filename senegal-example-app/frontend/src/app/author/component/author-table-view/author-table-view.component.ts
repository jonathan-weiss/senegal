import {Component, EventEmitter, Input, Output} from '@angular/core';
import { AuthorTO } from "../../api/author-to.model";


@Component({
    selector: 'author-table-view',
    templateUrl: './author-table-view.component.html',
    styleUrls: ['./author-table-view.component.scss'],
})
export class AuthorTableViewComponent {

    @Input() allAuthor!: ReadonlyArray<AuthorTO>
    @Input() highlightedAuthor: AuthorTO | undefined = undefined;
    @Input() tableControlsDisabled!: boolean;

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
      return this.highlightedAuthor != undefined && author.authorId.uuid == this.highlightedAuthor.authorId.uuid;
  }

    getEntries(): AuthorTO[] {
        return [...this.allAuthor];
    }

    onCtxEditClicked(entry: AuthorTO): void {
        this.editEntryClicked.emit(entry);
    }

    onRowDoubleClicked(entry: AuthorTO): void {
        this.editEntryClicked.emit(entry);
    }

    onCtxDeleteClicked(entry: AuthorTO): void {
        this.deleteEntryClicked.emit(entry);
    }

}
