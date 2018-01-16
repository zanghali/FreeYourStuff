import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalSpaceDialogComponent } from './personal-space-dialog.component';

describe('PersonalSpaceDialogComponent', () => {
  let component: PersonalSpaceDialogComponent;
  let fixture: ComponentFixture<PersonalSpaceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalSpaceDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalSpaceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
