import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {LocalStorageService} from '../../shared/local-storage.service';
import {CountryIdTO} from './country-id-to.model';
import {CountryTO} from './country-to.model';
import {SearchCountryInstructionTO} from './search-country-instruction-to.model';

@Injectable({
providedIn: 'root'
})
export class CountryLocalStorageApiService {

  constructor(private localStorageService: LocalStorageService) {}

    private findCountryIndexById(countryId: CountryIdTO): number {
      const countryIndex: number = this.getCountries().findIndex(author => author.countryId.value === countryId.value)
      if(countryIndex == -1) {
        throw new Error("Country index not found for " + countryId)
      }
      return countryIndex;
    }

    findCountyById(countryId: CountryIdTO): CountryTO {
      const country = this.getCountries().find(country => country.countryId.value === countryId.value)
      if(country == null) {
        throw new Error("Country not found for id " + countryId)
      }
      return country
    }

    getCountryById(authorId: CountryIdTO): Observable<CountryTO> {
        return of(this.findCountyById(authorId))
    }

    getAllCountry(): Observable<ReadonlyArray<CountryTO>> {
        return of(this.getCountries())
    }

    searchAllCountry(searchCriteria: SearchCountryInstructionTO): Observable<ReadonlyArray<CountryTO>> {
      return of(this.getCountries()) // TODO add search criteria
    }

  private initialCountryList: Array<CountryTO> = [
    {
      countryId: {
        value: "c6a41036-d44b-4284-976b-e57d9d2810b0"
      },
      countryName: "Switzerland",
    },
    {
      countryId: {
        value: "e57e8e9a-e1a0-4fbc-9955-aa041b412159"
      },
      countryName: "Germany",
    },
    {
      countryId: {
        value: "c1ffd873-ad51-4405-98e9-a80432d81d36"
      },
      countryName: "Austria",
    },
  ];

  private getCountries(): Array<CountryTO> {
    return this.localStorageService.getLocalStorageOrStoreDefault("COUNTRIES", this.initialCountryList) as Array<CountryTO>
  }

  private storeAuthors(countries: Array<CountryTO>) {
    this.localStorageService.saveLocalStorage("COUNTRIES", countries)
  }

}
