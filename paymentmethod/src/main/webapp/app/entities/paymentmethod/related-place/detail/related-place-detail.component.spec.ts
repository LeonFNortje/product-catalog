import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RelatedPlaceDetailComponent } from './related-place-detail.component';

describe('RelatedPlace Management Detail Component', () => {
  let comp: RelatedPlaceDetailComponent;
  let fixture: ComponentFixture<RelatedPlaceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelatedPlaceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ relatedPlace: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(RelatedPlaceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RelatedPlaceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load relatedPlace on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.relatedPlace).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
