import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[componentStackAnchor]',
})
export class ComponentStackAnchorDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
