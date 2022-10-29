import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BundledProductSpecificationDetailComponent } from './bundled-product-specification-detail.component';

describe('BundledProductSpecification Management Detail Component', () => {
  let comp: BundledProductSpecificationDetailComponent;
  let fixture: ComponentFixture<BundledProductSpecificationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BundledProductSpecificationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bundledProductSpecification: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(BundledProductSpecificationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BundledProductSpecificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bundledProductSpecification on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bundledProductSpecification).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
