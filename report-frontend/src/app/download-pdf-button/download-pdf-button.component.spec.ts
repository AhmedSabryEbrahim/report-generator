import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadPdfButtonComponent } from './download-pdf-button.component';

describe('DownloadPdfButtonComponent', () => {
  let component: DownloadPdfButtonComponent;
  let fixture: ComponentFixture<DownloadPdfButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadPdfButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadPdfButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
