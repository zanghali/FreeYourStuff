import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemCreationDialogComponent } from './item-creation-dialog.component';

describe('ItemCreationDialogComponent', () => {
  let component: ItemCreationDialogComponent;
  let fixture: ComponentFixture<ItemCreationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemCreationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemCreationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
