import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ErrorMessage} from "../../../shared/error-list/error-message.model";
import {ErrorTransformationService} from "../../../shared/error-list/error-transformation.service";
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatButtonModule} from '@angular/material/button';
import {SearchCountryInstructionTO} from '../../api/search-country-instruction-to.model';
import {CountryService} from '../../country.service';
import {CountryTO} from '../../api/country-to.model';
import {CountryResultComponent} from '../country-result/country-result.component';


@Component({
  selector: 'country-search-view',
  templateUrl: './country-search-view.component.html',
  styleUrls: ['./country-search-view.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatTabsModule,
    ErrorListComponent,
    MatButtonModule,
    CountryResultComponent,
  ]
})
export class CountrySearchViewComponent implements OnInit {
  @Input() isLocked!: boolean

  @Output() selectClicked: EventEmitter<CountryTO> = new EventEmitter<CountryTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
  @Output() searchClicked: EventEmitter<SearchCountryInstructionTO> = new EventEmitter<SearchCountryInstructionTO>();

  allCountry: ReadonlyArray<CountryTO> = []

  highlightedCountry: CountryTO | undefined = undefined;

  errorMessages: Array<ErrorMessage> = []

  constructor(private countryService: CountryService,
              private errorTransformationService: ErrorTransformationService) {
  }

  ngOnInit(): void {
    this.loadAllCountry();
  }

  private loadAllCountry(): void {
    const searchCriteria: SearchCountryInstructionTO = {
      authorId: undefined,
      countryName: undefined,
    }

    this.countryService
      .searchAllCountry(searchCriteria)
      .subscribe((entities: ReadonlyArray<CountryTO>) => {
        this.allCountry = entities;
      });
  }

  select(country: CountryTO): void {
    this.selectClicked.emit(country);
  }

  cancel(): void {
    this.cancelClicked.emit();
  }
}
