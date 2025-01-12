import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {SearchCountryInstructionTO} from '../../api/search-country-instruction-to.model';


@Component({
  selector: 'country-search-box',
  templateUrl: './country-search-box.component.html',
  styleUrls: ['./country-search-box.component.scss'],
  standalone: true,
  imports: [
    MatButtonModule,
  ]
})
export class CountrySearchBoxComponent {
  @Input() isLocked!: boolean

  @Output() searchClicked: EventEmitter<SearchCountryInstructionTO> = new EventEmitter<SearchCountryInstructionTO>();

  searchButtonClicked(): void {
    const searchCriteria: SearchCountryInstructionTO = {
      authorId: undefined,
      countryName: undefined,
    }
    this.searchClicked.emit(searchCriteria)
  }
}
