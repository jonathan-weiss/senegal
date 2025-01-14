import {ColumnEntry} from './column-entry.model';

export class ColumnUtil {
  public static createColumnEntry(columnIdentifier: string): ColumnEntry {
    return {
      columnIdentifier: columnIdentifier,
      columnName: columnIdentifier
    };
  }

  public static copyColumnSelection(selectedColumnIdentifiers: ReadonlyArray<string>): ReadonlyArray<string> {
    return selectedColumnIdentifiers.concat([])
  }

}
