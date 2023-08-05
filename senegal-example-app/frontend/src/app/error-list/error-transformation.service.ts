import {Injectable} from '@angular/core';
import {ErrorMessage} from "./error-message.model";


@Injectable({
  providedIn: 'root',
})
export class ErrorTransformationService {


  transformError(entityDescription: string, error: any | undefined): ErrorMessage | undefined {
    if(error == undefined) {
      return undefined
    }
    const errorMessage = error['statusText'];
    if(errorMessage == undefined) {
      return undefined
    }

    return {
      errorTitle: entityDescription,
      errorMessage: errorMessage,
    }
  }
}
