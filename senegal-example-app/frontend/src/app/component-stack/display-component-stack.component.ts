import {Component, ComponentRef, ElementRef, Input, OnDestroy, OnInit, Type, ViewChild, ViewRef} from '@angular/core';
import {ComponentStackAnchorDirective} from "./component-stack-anchor.directive";
import {ComponentStackObserver} from "./component-stack-observer.interface";
import {ComponentStackObservationService} from "./component-stack-observation.service";
import {StackEntry} from "./stack-entry.interface";
import {StackKey} from "./stack-key";
import {C} from "@angular/cdk/keycodes";


@Component({
  selector: 'display-component-stack',
  templateUrl: './display-component-stack.component.html',
  styleUrls: ['./display-component-stack.component.scss'],
})
export class DisplayComponentStackComponent implements OnInit, OnDestroy, ComponentStackObserver {

  @Input() stackSelector!: StackKey;

  private stackComponents: Array<StackEntry> = [];

  @ViewChild(ComponentStackAnchorDirective, {static: true}) stackAnchor!: ComponentStackAnchorDirective;
  @ViewChild("endOfPage", {static: true, read: ElementRef}) endOfPage!: ElementRef;


  constructor(private readonly panelStackObservationService: ComponentStackObservationService) {
  }

  ngOnInit(): void {
    this.panelStackObservationService.registerForStackObservation(this.stackSelector, this);
  }

  ngOnDestroy(): void {
    this.panelStackObservationService.unregisterOfStackObservation(this.stackSelector, this);
  }

  addComponentToStack<C extends StackEntry>(componentStackEntry: Type<C>, onInitialization: (component: C) => void): void {
    const componentRef: ComponentRef<C> = this.stackAnchor.viewContainerRef.createComponent<C>(componentStackEntry);
    const stackComponent: C = componentRef.instance
    onInitialization(stackComponent);
    this.forAllStackComponents((stackEntry: StackEntry) => {
      stackEntry.lock();
    })
    this.stackComponents.push(stackComponent);

    setTimeout(() => { this.scrollToEnd()});
  }

  private scrollToEnd(): void {
    this.endOfPage.nativeElement.scrollIntoView({
      behavior: 'smooth',
      block: 'end',
    });
  }

  removeLatestComponentFromStack(): void {
    this.stackComponents.pop();
    this.forLastStackComponent((stackEntry: StackEntry) => {
      stackEntry.unlock();
    })
    this.stackAnchor.viewContainerRef.remove()
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
