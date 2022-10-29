import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSpecificationRelationshipDetailComponent } from './product-specification-relationship-detail.component';

describe('ProductSpecificationRelationship Management Detail Component', () => {
  let comp: ProductSpecificationRelationshipDetailComponent;
  let fixture: ComponentFixture<ProductSpecificationRelationshipDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSpecificationRelationshipDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSpecificationRelationship: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductSpecificationRelationshipDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSpecificationRelationshipDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSpecificationRelationship on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSpecificationRelationship).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
