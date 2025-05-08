import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MyDragAndDropComponent } from './my-drag-and-drop.component';

describe('MyDragAndDropComponent', () => {
  let component: MyDragAndDropComponent;
  let fixture: ComponentFixture<MyDragAndDropComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDragAndDropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
