import {NgControl} from '@angular/forms';
import {Directive, Input, OnChanges, SimpleChanges} from "@angular/core";

@Directive({
  selector: '[disableFormControl]'
})
export class DisableFormControlDirective implements OnChanges {

  @Input() disableFormControl!: Boolean;

  private static formEnableDisableOptions = {
    onlySelf: true,
    emitEvent:false,
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.setFormControlDisabledOrEnabled();
  }

  private setFormControlDisabledOrEnabled(): void {
    const formControl = this.control.control
    if(formControl != undefined) {
      if(this.disableFormControl) {
        formControl.disable(DisableFormControlDirective.formEnableDisableOptions)
      } else {
        formControl.enable(DisableFormControlDirective.formEnableDisableOptions)
      }
    }
  }

  constructor( private control : NgControl ) {
  }

}
