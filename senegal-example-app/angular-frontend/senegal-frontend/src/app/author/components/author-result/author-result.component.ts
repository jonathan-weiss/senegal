import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';


@Component({
  selector: 'author-result',
  templateUrl: './author-result.component.html',
  styleUrls: ['./author-result.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
  ]
})
export class AuthorResultComponent {
  @Input() showSelectButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean;

  @Input() allAuthor!: ReadonlyArray<AuthorTO>
  @Input() highlightedAuthor: AuthorTO | undefined = undefined;

  @Output() selectEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() editEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() deleteEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();

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

  onRowClicked(entry: AuthorTO): void {
    this.onSelectEntry(entry)
  }

  onRowDoubleClicked(entry: AuthorTO): void {
      if(this.showSelectButton) {
        this.onSelectEntry(entry)
      } else if(this.showEditButton) {
        this.onEditEntry(entry)
      }
  }

  editClicked(entry: AuthorTO): void {
    this.onEditEntry(entry)
  }

  selectClicked(entry: AuthorTO): void {
    this.onSelectEntry(entry)
  }

  deleteClicked(entry: AuthorTO): void {
    this.onDeleteEntry(entry)
  }

  private onEditEntry(entry: AuthorTO): void {
    if(!this.isLocked) {
      this.editEntry.emit(entry);
    }
  }

  private onDeleteEntry(entry: AuthorTO): void {
    if(!this.isLocked) {
      this.deleteEntry.emit(entry);
    }
  }

  private onSelectEntry(entry: AuthorTO): void {
    if(!this.isLocked) {
      this.highlightedAuthor = entry
      this.selectEntry.emit(entry);
    }
  }
}
