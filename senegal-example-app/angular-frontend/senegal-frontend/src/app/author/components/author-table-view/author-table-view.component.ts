import {Component, EventEmitter, Input, Output} from '@angular/core';
import { AuthorTO } from "../../api/author-to.model";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {AuthorIdFormFieldComponent} from '../author-form-view/author-id-form-field/author-id-form-field.component';
import {
  AuthorFirstnameFormFieldComponent
} from '../author-form-view/author-firstname-form-field/author-firstname-form-field.component';
import {
  AuthorLastnameFormFieldComponent
} from '../author-form-view/author-lastname-form-field/author-lastname-form-field.component';
import {AllBookByAuthorComponent} from '../author-form-view/author-all-book-by-author/all-book-by-author.component';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';


@Component({
  selector: 'author-table-view',
  templateUrl: './author-table-view.component.html',
  styleUrls: ['./author-table-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
  ]
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
