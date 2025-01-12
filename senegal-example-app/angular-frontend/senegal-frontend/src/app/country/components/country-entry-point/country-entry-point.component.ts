import {Component, OnInit, ViewChild} from '@angular/core';
import {ErrorListComponent} from '../../../shared/error-list/error-list.component';
import {MatButton} from '@angular/material/button';
import {ErrorMessage} from '../../../shared/error-list/error-message.model';
import {ErrorTransformationService} from '../../../shared/error-list/error-transformation.service';
import {MatExpansionModule, MatExpansionPanel} from '@angular/material/expansion';
import {CountryResultComponent} from '../country-result/country-result.component';
import {CountrySearchBoxComponent} from '../country-search-box/country-search-box.component';
import {CountryService} from '../../country.service';
import {CountryTO} from '../../api/country-to.model';
import {SearchCountryInstructionTO} from '../../api/search-country-instruction-to.model';

@Component({
  selector: "country-entry-point",
  templateUrl: "country-entry-point.component.html",
  styleUrls: ["country-entry-point.component.scss"],
  standalone: true,
  imports: [
    CountryResultComponent,
    ErrorListComponent,
    MatButton,
    CountrySearchBoxComponent,
    MatExpansionPanel,
    MatExpansionModule,
  ]
})
export class CountryEntryPointComponent implements OnInit {


  @ViewChild("searchPanel")
  searchPanel!: MatExpansionPanel

  @ViewChild("resultPanel")
  resultPanel!: MatExpansionPanel


  allCountry: ReadonlyArray<CountryTO> = []

  highlightedCountry: CountryTO | undefined = undefined;

  errorMessages: Array<ErrorMessage> = []


  constructor(private countryService: CountryService,
              private errorTransformationService: ErrorTransformationService) {
  }

  ngOnInit(): void {
    this.initiallyLoadAllCountry();
  }

  isSearchAndResultLocked(): boolean {
    return false
  }

  selectEntry(entry: CountryTO): void {
    console.log("Country selected", entry)
  }



  isSearchPerformed(searchCriteria: SearchCountryInstructionTO): void {
    this.searchAllCountry(searchCriteria)
    this.resultPanel.open()
  }

  private initiallyLoadAllCountry(): void {
    const searchCriteria: SearchCountryInstructionTO = {
      authorId: undefined,
      countryName: undefined,
    }
    this.searchAllCountry(searchCriteria)
  }

  private searchAllCountry(searchCriteria: SearchCountryInstructionTO): void {
    this.countryService
      .searchAllCountry(searchCriteria)
      .subscribe((entities: ReadonlyArray<CountryTO>) => {
        this.allCountry = entities;
      });
  }
}
