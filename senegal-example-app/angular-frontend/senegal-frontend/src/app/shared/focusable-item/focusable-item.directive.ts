import {Directive, ElementRef} from '@angular/core';
import {FocusableOption, FocusOrigin} from '@angular/cdk/a11y';

@Directive({
  selector: '[focusable-item]',
  standalone: true,
  host: {
    tabindex: '-1',
    role: 'list-item',
  },
})
export class FocusableItemDirective implements FocusableOption {
  constructor(private element: ElementRef) { }

  focus(origin?: FocusOrigin): void {
    this.element.nativeElement.focus();
  }


}
