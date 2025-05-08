import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatSlideToggleChange, MatSlideToggleModule} from '@angular/material/slide-toggle';

@Component({
  selector: 'nullable-field',
  templateUrl: './nullable-field.component.html',
  styleUrls: ['./nullable-field.component.scss'],
  imports: [
    MatSlideToggleModule
  ],
  standalone: true
})
export class NullableFieldComponent {
  @Input() isNullFieldFormControl!: FormControl;

  @Input() isLocked!: boolean;

  @Output() isNullValue: EventEmitter<boolean> = new EventEmitter<boolean>();

  onToggleChange(event: MatSlideToggleChange): void {
    const isNull = !event.checked;
    this.isNullFieldFormControl.setValue(isNull);
    this.isNullValue.emit(isNull);
  }

  isFieldAvailable(): boolean {
    return !this.isFieldNull();
  }

  private isFieldNull(): boolean {
    return this.isNullFieldFormControl.value as boolean;
  }

  visibilityValue(): string {
    return this.isFieldAvailable() ? "visible" : "hidden";
  }
}
