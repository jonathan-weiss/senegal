import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from '@angular/material/table';
import {MatCheckbox} from '@angular/material/checkbox';
import {ColumnEntry} from '../column-entry.model';
import {ColumnSelectionEntry} from '../column-selection-entry.model';


@Component({
  selector: 'column-selection-table',
  templateUrl: './column-selection-table.component.html',
  styleUrls: ['./column-selection-table.component.scss'],
  standalone: true,
  imports: [
    MatTable,
    MatCell,
    MatCellDef,
    MatCheckbox,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatHeaderCellDef,
  ]
})
export class ColumnSelectionTableComponent implements OnInit {

  @Input() availableColumns: ReadonlyArray<ColumnEntry> = []
  @Input() selectedColumns: ReadonlyArray<string> = []

  @Output() selectionChanged: EventEmitter<ReadonlyArray<string>> = new EventEmitter()

  displayedColumns: ReadonlyArray<string> = ["columnSelection", "columnName"]
  columnIdentifiers: ReadonlyArray<string> = []
  columnSelections: ReadonlyArray<ColumnSelectionEntry> = []

  ngOnInit(): void {
    this.columnIdentifiers = this.availableColumns.map(entry => entry.columnIdentifier)
    this.columnSelections = this.availableColumns.map(entry => this.toSelectionEntry(entry))
  }

  private toSelectionEntry(columnEntry: ColumnEntry): ColumnSelectionEntry {
    const isSelected: boolean = this.selectedColumns
      .some((selectedColumn: string) => columnEntry.columnIdentifier == selectedColumn)
    return {
      column: columnEntry,
      isColumnSelected: isSelected,
    }
  }

  asColumnSelectionEntry(entry: any): ColumnSelectionEntry {
    return entry as ColumnSelectionEntry
  }

  columnSelectionCheckboxId(entry: ColumnSelectionEntry): string {
    return "column_selection_checkbox_" + entry.column.columnIdentifier
  }

  isSelectedAll(): boolean {
    return this.columnSelections
      .every((entry: ColumnSelectionEntry) => entry.isColumnSelected)
  }

  selectAllColumnClicked(selected: boolean): void {
    this.columnSelections.forEach((entry: ColumnSelectionEntry) => {
      entry.isColumnSelected = selected
    })
    this.emitSelectionChange()
  }

  columnSelectionClicked(columnSelection: ColumnSelectionEntry, selected: boolean): void {
    columnSelection.isColumnSelected = selected
    this.emitSelectionChange()
  }

  private emitSelectionChange(): void {
    const newColumnSelection: ReadonlyArray<string> = this.columnSelections
      .filter(entry => entry.isColumnSelected)
      .map(entry => entry.column.columnIdentifier)
    this.selectionChanged.emit(newColumnSelection)
  }
}
