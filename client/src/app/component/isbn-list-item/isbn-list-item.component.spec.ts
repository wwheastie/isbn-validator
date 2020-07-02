import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IsbnListItemComponent } from './isbn-list-item.component';

describe('IsbnListItemComponent', () => {
  let component: IsbnListItemComponent;
  let fixture: ComponentFixture<IsbnListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IsbnListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IsbnListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
