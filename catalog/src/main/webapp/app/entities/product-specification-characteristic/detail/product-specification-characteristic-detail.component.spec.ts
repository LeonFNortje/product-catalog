import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSpecificationCharacteristicDetailComponent } from './product-specification-characteristic-detail.component';

describe('ProductSpecificationCharacteristic Management Detail Component', () => {
  let comp: ProductSpecificationCharacteristicDetailComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSpecificationCharacteristicDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSpecificationCharacteristic: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductSpecificationCharacteristicDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSpecificationCharacteristicDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSpecificationCharacteristic on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSpecificationCharacteristic).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
