import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ColumnlistComponent } from './columnlist.component';

describe('ColumnlistComponent', () => {
  let component: ColumnlistComponent;
  let fixture: ComponentFixture<ColumnlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ColumnlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColumnlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
