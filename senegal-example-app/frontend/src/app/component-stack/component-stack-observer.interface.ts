import {Type} from "@angular/core";
import {StackEntry} from "./stack-entry.interface";
import {StackKey} from "./stack-key";
import {C} from "@angular/cdk/keycodes";

export interface ComponentStackObserver {

  addComponentToStack<C extends StackEntry>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void

  removeLatestComponentFromStack(): void
}
