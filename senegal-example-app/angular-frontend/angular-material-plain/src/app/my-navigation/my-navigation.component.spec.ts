import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyNavigationComponent } from './my-navigation.component';

describe('MyNavigationComponent', () => {
  let component: MyNavigationComponent;
  let fixture: ComponentFixture<MyNavigationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [MyNavigationComponent],
      imports: [
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatSidenavModule,
        MatToolbarModule,
      ]
    });
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyNavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
