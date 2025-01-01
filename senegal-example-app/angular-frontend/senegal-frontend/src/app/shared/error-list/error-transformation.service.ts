import {Injectable} from '@angular/core';
import {ErrorMessage} from "./error-message.model";


@Injectable({
  providedIn: 'root',
})
export class ErrorTransformationService {


  transformErrorToMessage(entityDescription: string, error: any | undefined): ErrorMessage | undefined {
    if(error == undefined) {
      return undefined
    }
    const errorMessage: string | undefined =
      this.httpErrorResponseWithErrorDescriptionMessage(error)
      ?? this.httpErrorResponseMessage(error);

    if(errorMessage == undefined) {
      return undefined
    }

    return {
      errorTitle: entityDescription,
      errorMessage: errorMessage,
    }
  }

  private httpErrorResponseWithErrorDescriptionMessage(error: any): string | undefined {
    const errorObject = error['error'];
    if(errorObject == undefined) {
      return undefined;
    }
    return errorObject['message'];
  }

  private httpErrorResponseMessage(error: any): string | undefined {
    return error['statusText'];

  }
}
