import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  private static readonly LOCAL_STORAGE_KEY_PREFIX: string = "SENEGAL_";

  public getLocalStorageOrStoreDefault(key: string, object: any): any | undefined {
    const value = this.getLocalStorage(key)
    if(value != null) {
      return value
    } else {
      this.saveLocalStorage(key, object)
      return object
    }
  }

  public saveLocalStorage(key: string, object: any): void {
    const prefixedKey = this.prefixedKey(key);
    console.log("Store", prefixedKey, " to local storage", object)
    const value = JSON.stringify(object)

    window.localStorage.setItem(prefixedKey, value)
  }

  public getLocalStorage(key: string): any | undefined {
    const value = window.localStorage.getItem(this.prefixedKey(key))
    if(value != null) {
      return JSON.parse(value)
    } else {
      return undefined
    }
  }

  private prefixedKey(key: String): string {
    return LocalStorageService.LOCAL_STORAGE_KEY_PREFIX + key;
  }

  clearLocalStorage() {
    const keyList: Array<string> = []
    for(let i = 0; i < window.localStorage.length; i++) {
      const key = window.localStorage.key(i)
      if(key != null && key.startsWith(LocalStorageService.LOCAL_STORAGE_KEY_PREFIX)) {
        keyList.push(key)
      }
    }
    keyList.forEach(key => window.localStorage.removeItem(key))
  }
}
