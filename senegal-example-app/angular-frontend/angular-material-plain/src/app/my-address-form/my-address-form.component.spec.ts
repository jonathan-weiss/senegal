import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAddressFormComponent } from './my-address-form.component';

describe('MyAddressFormComponent', () => {
  let component: MyAddressFormComponent;
  let fixture: ComponentFixture<MyAddressFormComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAddressFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
