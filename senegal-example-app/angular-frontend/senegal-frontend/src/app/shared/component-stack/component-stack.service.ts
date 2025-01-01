import {Injectable, Type} from '@angular/core';
import {ComponentStackObservationService} from "./component-stack-observation.service";
import {StackEntry} from "./stack-entry.interface";
import {StackKey} from "./stack-key";

@Injectable({
  providedIn: 'root',
})
export class ComponentStackService {
  constructor(private componentStackObservationService: ComponentStackObservationService) {
  }

  public newComponentOnStack<C extends StackEntry>(stackSelector: StackKey, componentStackEntry: Type<C>, onInitialization: (component: C) => void): void {
    this.componentStackObservationService.addComponentOntoStack(stackSelector, componentStackEntry, onInitialization);
  }

  public removeLatestComponentFromStack(stackSelector: StackKey): void {
      this.componentStackObservationService.removeLatestComponentFromStack(stackSelector);
  }
}
