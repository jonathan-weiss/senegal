import {CountryIdTO} from "./country-id-to.model";


export interface SearchCountryInstructionTO {
    authorId: CountryIdTO | undefined;
    countryName: string | undefined;
}

