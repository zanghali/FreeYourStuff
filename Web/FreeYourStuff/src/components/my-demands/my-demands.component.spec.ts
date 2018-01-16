import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDemandsComponent } from './my-demands.component';

describe('MyDemandsComponent', () => {
  let component: MyDemandsComponent;
  let fixture: ComponentFixture<MyDemandsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDemandsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDemandsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
