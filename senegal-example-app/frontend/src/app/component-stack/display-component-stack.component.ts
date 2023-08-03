import {Component, ComponentRef, OnDestroy, OnInit, Type, ViewChild} from '@angular/core';
import {ComponentStackAnchorDirective} from "./component-stack-anchor.directive";
import {ComponentStackObserver} from "./component-stack-observer.interface";
import {ComponentStackObservationService} from "./component-stack-observation.service";
import {StackEntry} from "./stack-entry.interface";


@Component({
  selector: 'display-component-stack',
  templateUrl: './display-component-stack.component.html',
  styleUrls: ['./display-component-stack.component.scss'],
})
export class DisplayComponentStackComponent implements OnInit, OnDestroy, ComponentStackObserver {

  private stackComponents: Array<StackEntry> = [];

  @ViewChild(ComponentStackAnchorDirective, {static: true}) stackAnchor!: ComponentStackAnchorDirective;


  constructor(private readonly panelStackObservationService: ComponentStackObservationService) {
  }

  ngOnInit(): void {
    this.panelStackObservationService.registerForStackObservation(this);
  }

  ngOnDestroy(): void {
    this.panelStackObservationService.unregisterOfStackObservation(this);
  }

  addComponentToStack<C extends StackEntry>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void {
    const componentRef: ComponentRef<C> = this.stackAnchor.viewContainerRef.createComponent<C>(componentStackEntry);
    const stackComponent: C = componentRef.instance
    onInitialization(stackComponent);
    this.forAllStackComponents((stackEntry: StackEntry) => {
      stackEntry.lock();
    })
    this.stackComponents.push(stackComponent);

  }

  removeLatestComponentFromStack(): void {
    this.stackComponents.pop();
    this.forLastStackComponent((stackEntry: StackEntry) => {
      stackEntry.unlock();
    })
    this.stackAnchor.viewContainerRef.remove()
  }

  removeAllComponentsFromStack(): void {
    this.stackAnchor.viewContainerRef.clear();
  }

  private forAllStackComponents(callback: (stackEntry: StackEntry) => void): void {
    this.stackComponents.forEach((stackEntry: StackEntry) => {
      callback(stackEntry)
    })
  }

  private forLastStackComponent(callback: (stackEntry: StackEntry) => void): void {
    const length = this.stackComponents.length;
    if(length > 0) {
      callback(this.stackComponents[length -1]);
    }
  }

}
