import {Component, ComponentRef, OnDestroy, OnInit, Type, ViewChild} from '@angular/core';
import {ComponentStackAnchorDirective} from "./component-stack-anchor.directive";
import {ComponentStackObserver} from "./component-stack-observer.interface";
import {ComponentStackObservationService} from "./component-stack-observation.service";


@Component({
  selector: 'display-component-stack',
  templateUrl: './display-component-stack.component.html',
  styleUrls: ['./display-component-stack.component.scss'],
})
export class DisplayComponentStackComponent implements OnInit, OnDestroy, ComponentStackObserver {

  @ViewChild(ComponentStackAnchorDirective, {static: true}) stackAnchor!: ComponentStackAnchorDirective;


  constructor(private readonly panelStackObservationService: ComponentStackObservationService) {
  }

  ngOnInit(): void {
    this.panelStackObservationService.registerForStackObservation(this);
  }

  ngOnDestroy(): void {
    this.panelStackObservationService.unregisterOfStackObservation(this);
  }

  addComponentToStack(componentStackEntry: Type<any>, onInitialization: (component: any) => void): void {
    const componentRef: ComponentRef<any> = this.stackAnchor.viewContainerRef.createComponent<any>(componentStackEntry);
    onInitialization(componentRef.instance);
  }

  removeLatestComponentFromStack(): void {
    this.stackAnchor.viewContainerRef.remove()
  }

  removeAllComponentsFromStack(): void {
    this.stackAnchor.viewContainerRef.clear();
  }


}
