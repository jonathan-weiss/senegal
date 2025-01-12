import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {CountryLocalStorageApiService} from "./api/country-local-storage-api.service";
import {CountryTO} from './api/country-to.model';
import {SearchCountryInstructionTO} from './api/search-country-instruction-to.model';


@Injectable({
  providedIn: 'root',
})
export class CountryService {

  constructor(private readonly countryApiService: CountryLocalStorageApiService) {
  }

  getAllCountry(): Observable<ReadonlyArray<CountryTO>> {
    return this.countryApiService.getAllCountry();
  }

  searchAllCountry(searchCriteria: SearchCountryInstructionTO): Observable<ReadonlyArray<CountryTO>> {
    return this.countryApiService.searchAllCountry(searchCriteria).pipe(
      map( (entities) => entities)
    );
  }
}
