import {ColumnEntry} from '../column-entry.model';

export interface ColumnSelectionDialogData {
  availableColumns: ReadonlyArray<ColumnEntry>
  selectedColumns: ReadonlyArray<string>

}
