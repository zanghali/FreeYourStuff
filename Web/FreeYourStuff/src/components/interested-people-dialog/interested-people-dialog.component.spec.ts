import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestedPeopleDialogComponent } from './interested-people-dialog.component';

describe('InterestedPeopleDialogComponent', () => {
  let component: InterestedPeopleDialogComponent;
  let fixture: ComponentFixture<InterestedPeopleDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterestedPeopleDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestedPeopleDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
