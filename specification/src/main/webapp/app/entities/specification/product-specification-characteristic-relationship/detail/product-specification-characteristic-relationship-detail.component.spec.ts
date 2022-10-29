import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSpecificationCharacteristicRelationshipDetailComponent } from './product-specification-characteristic-relationship-detail.component';

describe('ProductSpecificationCharacteristicRelationship Management Detail Component', () => {
  let comp: ProductSpecificationCharacteristicRelationshipDetailComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicRelationshipDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSpecificationCharacteristicRelationshipDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSpecificationCharacteristicRelationship: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductSpecificationCharacteristicRelationshipDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSpecificationCharacteristicRelationshipDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSpecificationCharacteristicRelationship on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSpecificationCharacteristicRelationship).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
