import {Type} from "@angular/core";

export interface ComponentStackObserver {

  addComponentToStack(componentStackEntry: Type<any>, onInitialization: (component: any) => void): void
  removeLatestComponentFromStack(): void
  removeAllComponentsFromStack(): void;
}
