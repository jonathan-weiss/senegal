import {Injectable, Type} from '@angular/core';
import {ComponentStackObserver} from "./component-stack-observer.interface";
import {StackEntry} from "./stack-entry.interface";
import {StackKey} from "./stack-key";
import {C} from "@angular/cdk/keycodes";
import {CollectionsUtil} from "../commons/collections.util";


@Injectable({
  providedIn: 'root',
})
export class ComponentStackObservationService {
  private stackObservers: Map<StackKey, Set<ComponentStackObserver>> = new Map<StackKey, Set<ComponentStackObserver>>();

  public addComponentOntoStack<C extends StackEntry>(stackSelector: StackKey, component: Type<C>, onInitialization: (component: C) => void): void {
    this.stackObserversForStackSelector(stackSelector).forEach((componentStackObserver: ComponentStackObserver) =>  {
      componentStackObserver.addComponentToStack(component, onInitialization);
    });
  }

  public removeLatestComponentFromStack(stackSelector: StackKey): void {
    console.log("removeLatestComponentFromStack", stackSelector)
    this.stackObserversForStackSelector(stackSelector).forEach((componentStackObserver: ComponentStackObserver) =>  {
        componentStackObserver.removeLatestComponentFromStack();
      });
  }

  public registerForStackObservation(stackSelector: StackKey, componentStackObserver: ComponentStackObserver): void {
    const observerSet: Set<ComponentStackObserver> = this.stackObserversForStackSelector(stackSelector)
    if(!observerSet.has(componentStackObserver)) {
      observerSet.add(componentStackObserver);
    }
  }

  public unregisterOfStackObservation(stackSelector: StackKey, componentStackObserver: ComponentStackObserver): void {
    const observerSet: Set<ComponentStackObserver> = this.stackObserversForStackSelector(stackSelector)
    if(observerSet.has(componentStackObserver)) {
      observerSet.delete(componentStackObserver);
    }
  }

  private stackObserversForStackSelector(stackSelector: StackKey): Set<ComponentStackObserver> {
    const observerSet: Set<ComponentStackObserver> | undefined = this.stackObservers.get(stackSelector)

    if(observerSet == undefined) {
      const newSet: Set<ComponentStackObserver> = new Set();
      this.stackObservers.set(stackSelector, newSet);
      return newSet
    }

    return observerSet;
  }
}
