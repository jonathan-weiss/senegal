import {Type} from "@angular/core";
import {StackEntry} from "./stack-entry.interface";

export interface ComponentStackObserver {

  addComponentToStack<C extends StackEntry>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void
  removeLatestComponentFromStack(): void
  removeAllComponentsFromStack(): void;
}
