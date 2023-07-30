import {Injectable, Type} from '@angular/core';
import {ComponentStackObservationService} from "./component-stack-observation.service";

@Injectable({
  providedIn: 'root',
})
export class ComponentStackService {
  constructor(private componentStackObservationService: ComponentStackObservationService) {
  }

  public newComponentOnStack<C>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void {
    this.componentStackObservationService.addComponentOntoStack(componentStackEntry, onInitialization);
  }

  public removeLatestComponentFromStack(): void {
      this.componentStackObservationService.removeLatestComponentFromStack();
  }
}
