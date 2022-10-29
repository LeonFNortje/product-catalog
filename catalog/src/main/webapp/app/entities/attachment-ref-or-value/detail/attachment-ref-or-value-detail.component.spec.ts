import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AttachmentRefOrValueDetailComponent } from './attachment-ref-or-value-detail.component';

describe('AttachmentRefOrValue Management Detail Component', () => {
  let comp: AttachmentRefOrValueDetailComponent;
  let fixture: ComponentFixture<AttachmentRefOrValueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AttachmentRefOrValueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ attachmentRefOrValue: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(AttachmentRefOrValueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AttachmentRefOrValueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load attachmentRefOrValue on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.attachmentRefOrValue).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
