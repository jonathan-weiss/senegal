import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatChipListbox, MatChipOption} from '@angular/material/chips';
import {MatCheckbox} from '@angular/material/checkbox';
import {AuthorIdTO} from '../../api/author-id-to.model';
import {JsonPipe} from '@angular/common';


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
    MatChipListbox,
    MatChipOption,
    MatCheckbox,
    JsonPipe,
  ]
})
export class AuthorResultComponent implements OnChanges {
  @Input() showChoiceButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean;

  @Input() allAuthor!: ReadonlyArray<AuthorTO>
  @Input() highlightedAuthor: AuthorTO | undefined = undefined;

  @Output() chooseEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() editEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() deleteEntries: EventEmitter<ReadonlyArray<AuthorTO>> = new EventEmitter<ReadonlyArray<AuthorTO>>();

  displayedColumns: ReadonlyArray<string> = [];

  ngOnChanges(changes: SimpleChanges) {
    this.displayedColumns = this.calculateDisplayedColumns()
  }

  private calculateDisplayedColumns(): ReadonlyArray<string> {
    const calculatedArray: Array<string> = []

    if(this.showChoiceButton) {
      calculatedArray.push('choiceColumn')
    }

    if(this.showDeleteButton) {
      calculatedArray.push('selection')
    }

    calculatedArray.push(
      'authorId',
      'firstname',
      'lastname',
    )

    if(this.showEditButton) {
      calculatedArray.push('editColumn')
    }

    if(this.showDeleteButton) {
      calculatedArray.push('deleteColumn')
    }

    return calculatedArray
  }

  selectedAuthorMap: Map<AuthorIdTO, AuthorTO> = new Map()

  asAuthor(entry: any): AuthorTO {
    return entry as AuthorTO
  }

  isHighlighted(author: AuthorTO): boolean {
      return this.highlightedAuthor != undefined && author.authorId.value == this.highlightedAuthor.authorId.value;
  }

  onRowClicked(entry: AuthorTO): void {
    this.onChoseEntry(entry)
  }

  onRowDoubleClicked(entry: AuthorTO): void {
      if(this.showChoiceButton) {
        this.onChoseEntry(entry)
      } else if(this.showEditButton) {
        this.onEditEntry(entry)
      }
  }

  editClicked(entry: AuthorTO): void {
    this.onEditEntry(entry)
  }

  isSelected(entry: AuthorTO): boolean {
    return this.selectedAuthorMap.has(entry.authorId)
  }

  isSelectedAll: boolean = false

  selectedClicked(entry: AuthorTO, checked: boolean): void {
    if(checked) {
      this.selectedAuthorMap.set(entry.authorId, entry)
    } else {
      this.isSelectedAll = false
      this.selectedAuthorMap.delete(entry.authorId)
    }
  }

  selectAllClicked(checked: boolean): void {
    this.isSelectedAll = false
    this.selectedAuthorMap.clear()
    if(checked) {
      this.isSelectedAll = true
      this.allAuthor.forEach((author: AuthorTO) => {
        this.selectedAuthorMap.set(author.authorId, author)
      })
    }
  }

  deleteAllSelectedClicked(): void {
    if(!this.isLocked) {
      this.deleteEntries.emit(Array.from(this.selectedAuthorMap.values()));
    }
  }

  chooseClicked(entry: AuthorTO): void {
    this.onChoseEntry(entry)
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
      this.deleteEntries.emit([entry]);
    }
  }

  private onChoseEntry(entry: AuthorTO): void {
    if(!this.isLocked) {
      this.highlightedAuthor = entry
      this.chooseEntry.emit(entry);
    }
  }
}
