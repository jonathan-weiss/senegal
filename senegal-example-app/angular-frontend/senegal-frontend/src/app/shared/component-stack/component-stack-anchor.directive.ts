import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[componentStackAnchor]',
  standalone: true
})
export class ComponentStackAnchorDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
