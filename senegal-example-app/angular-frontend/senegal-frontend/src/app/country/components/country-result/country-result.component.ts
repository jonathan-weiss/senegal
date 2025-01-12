import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {CountryTO} from '../../api/country-to.model';


@Component({
  selector: 'country-result',
  templateUrl: './country-result.component.html',
  styleUrls: ['./country-result.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
  ]
})
export class CountryResultComponent {
  @Input() isLocked!: boolean;

  @Input() allCountry!: ReadonlyArray<CountryTO>
  @Input() highlightedCountry: CountryTO | undefined = undefined;

  @Output() selectEntryClicked: EventEmitter<CountryTO> = new EventEmitter<CountryTO>();

  displayedColumns: string[] = [
      'countryId',
      'countryName',
      'context'
  ];

  asCountry(entry: any): CountryTO {
    return entry as CountryTO
  }

  isHighlighted(country: CountryTO): boolean {
      return this.highlightedCountry != undefined && country.countryId.value == this.highlightedCountry.countryId.value;
  }

  onRowDoubleClicked(entry: CountryTO): void {
    this.selectClicked(entry)
  }

  selectClicked(entry: CountryTO): void {
    this.selectEntryClicked.emit(entry);
  }
}
