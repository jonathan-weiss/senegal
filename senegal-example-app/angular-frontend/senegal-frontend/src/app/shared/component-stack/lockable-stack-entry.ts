import {StackEntry} from "./stack-entry.interface";
import {LockStatus} from "./lock-status.interface";

export abstract class LockableStackEntry implements LockStatus, StackEntry {

  isLocked: boolean = false;

  lock(): void {
    this.isLocked = true;
  }

  unlock(): void {
    this.isLocked = false;
  }

}
