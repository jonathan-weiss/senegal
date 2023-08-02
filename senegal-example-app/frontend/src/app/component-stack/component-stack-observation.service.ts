import {Injectable, Type} from '@angular/core';
import {ComponentStackObserver} from "./component-stack-observer.interface";


@Injectable({
  providedIn: 'root',
})
export class ComponentStackObservationService {
  private stackObservers: Set<ComponentStackObserver> = new Set<ComponentStackObserver>();

  public addComponentOntoStack(component: Type<any>, onInitialization: (component: any) => void): void {
    this.stackObservers.forEach((componentStackObserver: ComponentStackObserver) =>  {
      componentStackObserver.addComponentToStack(component, onInitialization);
    });
  }

  public removeLatestComponentFromStack(): void {
      this.stackObservers.forEach((componentStackObserver: ComponentStackObserver) =>  {
        componentStackObserver.removeLatestComponentFromStack();
      });
  }

  public removeAllComponentFromStack() {
    this.stackObservers.forEach((componentStackObserver: ComponentStackObserver) =>  {
      componentStackObserver.removeAllComponentsFromStack();
    });

  }


  public registerForStackObservation(componentStackObserver: ComponentStackObserver): void {
    if(!this.stackObservers.has(componentStackObserver)) {
      this.stackObservers.add(componentStackObserver);
    }
  }

  public unregisterOfStackObservation(componentStackObserver: ComponentStackObserver): void {
    if(this.stackObservers.has(componentStackObserver)) {
      this.stackObservers.delete(componentStackObserver);
    }
  }

}
