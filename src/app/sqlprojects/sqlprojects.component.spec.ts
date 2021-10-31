import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SqlprojectsComponent } from './sqlprojects.component';

describe('SqlprojectsComponent', () => {
  let component: SqlprojectsComponent;
  let fixture: ComponentFixture<SqlprojectsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SqlprojectsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SqlprojectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
