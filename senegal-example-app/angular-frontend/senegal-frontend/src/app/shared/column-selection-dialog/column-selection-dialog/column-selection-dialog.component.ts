import {Component, inject} from '@angular/core';
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
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef
} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {ColumnSelectionTableComponent} from '../column-selection-table/column-selection-table.component';
import {ColumnEntry} from '../column-entry.model';
import {ColumnSelectionDialogData} from './column-selection-dialog-data.model';
import {ColumnUtil} from '../column.util';


@Component({
  selector: 'column-selection-dialog',
  templateUrl: './column-selection-dialog.component.html',
  styleUrls: ['./column-selection-dialog.component.scss'],
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
    ColumnSelectionTableComponent,
    MatDialogContent,
    MatButton,
    MatDialogClose,
    ColumnSelectionTableComponent,
    MatDialogActions,
  ]
})
export class ColumnSelectionDialogComponent {

  readonly dialogRef: MatDialogRef<ColumnSelectionDialogData> = inject(MatDialogRef<ColumnSelectionDialogComponent>);
  readonly data: ColumnSelectionDialogData = inject<ColumnSelectionDialogData>(MAT_DIALOG_DATA);

  selectedColumns: ReadonlyArray<string> = ColumnUtil.copyColumnSelection(this.data.selectedColumns)

  selectionChanged(selectedColumns: ReadonlyArray<string>): void {
    this.selectedColumns = selectedColumns
  }

  cancelClick(): void {
    this.dialogRef.close()
  }

  submitClick(): ReadonlyArray<string> {
    return this.selectedColumns
  }

}
