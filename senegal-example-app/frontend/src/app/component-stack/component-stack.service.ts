import {Injectable, Type} from '@angular/core';
import {ComponentStackObservationService} from "./component-stack-observation.service";
import {StackEntry} from "./stack-entry.interface";

@Injectable({
  providedIn: 'root',
})
export class ComponentStackService {
  constructor(private componentStackObservationService: ComponentStackObservationService) {
  }

  public newComponentOnStack<C extends StackEntry>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void {
    this.componentStackObservationService.addComponentOntoStack(componentStackEntry, onInitialization);
  }

  public removeLatestComponentFromStack(): void {
      this.componentStackObservationService.removeLatestComponentFromStack();
  }

  public resetComponentStack() {
    this.componentStackObservationService.removeAllComponentFromStack();
  }
}
