import { v4 } from 'uuid';
import {UuidTO} from "../uuid-to.model";

export class UuidUtil {

  private static createNewRandomUuid(): string {
    return v4();
  }

  public static generateNewUuid(): UuidTO {

    const uuidString: string = this.createNewRandomUuid();
    return this.createFromString(uuidString);
  }

  public static createFromString(uuidString: string): UuidTO {
    return {
      uuid: uuidString,
    };
  }

  public static isEqual(uuidA: UuidTO, uuidB: UuidTO): boolean {
    return uuidA.uuid === uuidB.uuid;
  }


}
