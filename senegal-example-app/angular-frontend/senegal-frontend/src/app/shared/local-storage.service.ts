import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  public getLocalStorageOrStoreDefault(key: string, object: any): any | undefined {
    const value = window.localStorage.getItem(key)
    if(value != null) {
      return JSON.parse(value)
    } else {
      this.saveLocalStorage(key, object)
      return object
    }
  }

  public saveLocalStorage(key: string, object: any): void {
    console.log("Store", key, " to local storage", object)
    const value = JSON.stringify(object)

    window.localStorage.setItem(key, value)
  }

  public getLocalStorage(key: string): any | undefined {
    const value = window.localStorage.getItem(key)
    if(value != null) {
      return JSON.parse(value)
    } else {
      return undefined
    }
  }
}
