import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RelatedPartyDetailComponent } from './related-party-detail.component';

describe('RelatedParty Management Detail Component', () => {
  let comp: RelatedPartyDetailComponent;
  let fixture: ComponentFixture<RelatedPartyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelatedPartyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ relatedParty: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(RelatedPartyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RelatedPartyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load relatedParty on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.relatedParty).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
