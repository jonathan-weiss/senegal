import {Type} from "@angular/core";

export interface ComponentStackObserver {

  componentAddedToStack(componentStackEntry: Type<any>, onInitialization: (component: any) => void): void
  latestComponentRemovedFromStack(): void

}
