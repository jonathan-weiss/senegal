import {
  AfterViewInit,
  Component,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
  QueryList,
  ViewChild,
  ViewChildren
} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {ReactiveFormsModule} from '@angular/forms';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCheckbox} from '@angular/material/checkbox';
import {AuthorIdTO} from '../../api/author-id-to.model';
import {
  DeleteButtonWithConfirmationComponent
} from '../../../shared/delete-button-with-confirmation/delete-button-with-confirmation.component';
import {ColumnEntry} from '../../../shared/column-selection-dialog/column-entry.model';
import {ColumnUtil} from '../../../shared/column-selection-dialog/column.util';
import {MatDialog} from '@angular/material/dialog';
import {
  ColumnSelectionDialogComponent
} from '../../../shared/column-selection-dialog/column-selection-dialog/column-selection-dialog.component';
import {
  ColumnSelectionDialogData
} from '../../../shared/column-selection-dialog/column-selection-dialog/column-selection-dialog-data.model';
import {MatSort, MatSortHeader, MatSortModule} from '@angular/material/sort';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';


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
    MatCheckbox,
    DeleteButtonWithConfirmationComponent,
    MatSortModule,
    MatPaginatorModule,
  ]
})
export class AuthorResultComponent implements OnInit, AfterViewInit {
  @Input() showChoiceButton: boolean = false
  @Input() showEditButton: boolean = false
  @Input() showDeleteButton: boolean = false
  @Input() isLocked!: boolean;

  @Input() allAuthor!: ReadonlyArray<AuthorTO>
  @Input() highlightedAuthor: AuthorTO | undefined = undefined;

  @Output() chooseEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() editEntry: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() deleteEntries: EventEmitter<ReadonlyArray<AuthorTO>> = new EventEmitter<ReadonlyArray<AuthorTO>>();

  allAuthorDataSource!: MatTableDataSource<AuthorTO>
  readonly columnSelectionDialog: MatDialog = inject(MatDialog);

  displayedColumns: ReadonlyArray<string> = [];

  allColumns: ReadonlyArray<ColumnEntry> = []
  selectedColumns: ReadonlyArray<string> = []

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit() {
    this.allColumns = [
      ColumnUtil.createColumnEntry('authorId'),
      ColumnUtil.createColumnEntry('firstname'),
      ColumnUtil.createColumnEntry('lastname'),
    ]
    this.selectedColumns = [
      'authorId',
      'firstname',
      'lastname',
    ]
    this.displayedColumns = this.calculateDisplayedColumns()

    this.allAuthorDataSource = new MatTableDataSource(Array.from(this.allAuthor))
  }

  ngAfterViewInit() {
    this.allAuthorDataSource.paginator = this.paginator;
    this.allAuthorDataSource.sort = this.sort;
  }

  private calculateDisplayedColumns(): ReadonlyArray<string> {
    const calculatedArray: Array<string> = []

    if(this.showChoiceButton) {
      calculatedArray.push('choiceColumn')
    }

    if(this.showDeleteButton) {
      calculatedArray.push('selection')
    }

    calculatedArray.push(... this.selectedColumns)

    calculatedArray.push('actionColumn')

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

  openColumnSelectionDialog(): void {
    const dialogData: ColumnSelectionDialogData = {
      availableColumns: this.allColumns,
      selectedColumns: this.selectedColumns
    }
    const dialogRef = this.columnSelectionDialog.open(ColumnSelectionDialogComponent, {
      data: dialogData,
    });

    dialogRef.afterClosed().subscribe((result: ReadonlyArray<string> | undefined) => {
      if (result !== undefined) {
        this.selectedColumns = result
        this.displayedColumns = this.calculateDisplayedColumns()
      }
    });
  }
}
