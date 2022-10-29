import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSpecificationCharacteristicFormService } from './product-specification-characteristic-form.service';
import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';
import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from 'app/entities/product-specification-characteristic-relationship/service/product-specification-characteristic-relationship.service';

import { ProductSpecificationCharacteristicUpdateComponent } from './product-specification-characteristic-update.component';

describe('ProductSpecificationCharacteristic Management Update Component', () => {
  let comp: ProductSpecificationCharacteristicUpdateComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSpecificationCharacteristicFormService: ProductSpecificationCharacteristicFormService;
  let productSpecificationCharacteristicService: ProductSpecificationCharacteristicService;
  let productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSpecificationCharacteristicUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProductSpecificationCharacteristicUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSpecificationCharacteristicUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSpecificationCharacteristicFormService = TestBed.inject(ProductSpecificationCharacteristicFormService);
    productSpecificationCharacteristicService = TestBed.inject(ProductSpecificationCharacteristicService);
    productSpecificationCharacteristicRelationshipService = TestBed.inject(ProductSpecificationCharacteristicRelationshipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductSpecificationCharacteristicRelationship query and add missing value', () => {
      const productSpecificationCharacteristic: IProductSpecificationCharacteristic = { id: 'CBA' };
      const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = {
        id: '0933e94f-afa0-47d7-8353-1a0ed95825c4',
      };
      productSpecificationCharacteristic.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;

      const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
        { id: '35cd22c1-ae3f-4faf-aa1e-b2fae6daf90c' },
      ];
      jest
        .spyOn(productSpecificationCharacteristicRelationshipService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: productSpecificationCharacteristicRelationshipCollection })));
      const additionalProductSpecificationCharacteristicRelationships = [productSpecificationCharacteristicRelationship];
      const expectedCollection: IProductSpecificationCharacteristicRelationship[] = [
        ...additionalProductSpecificationCharacteristicRelationships,
        ...productSpecificationCharacteristicRelationshipCollection,
      ];
      jest
        .spyOn(
          productSpecificationCharacteristicRelationshipService,
          'addProductSpecificationCharacteristicRelationshipToCollectionIfMissing'
        )
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecificationCharacteristic });
      comp.ngOnInit();

      expect(productSpecificationCharacteristicRelationshipService.query).toHaveBeenCalled();
      expect(
        productSpecificationCharacteristicRelationshipService.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing
      ).toHaveBeenCalledWith(
        productSpecificationCharacteristicRelationshipCollection,
        ...additionalProductSpecificationCharacteristicRelationships.map(expect.objectContaining)
      );
      expect(comp.productSpecificationCharacteristicRelationshipsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productSpecificationCharacteristic: IProductSpecificationCharacteristic = { id: 'CBA' };
      const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = {
        id: 'c87cf676-e98c-4b59-a7c4-c8c2eb4d1fa7',
      };
      productSpecificationCharacteristic.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;

      activatedRoute.data = of({ productSpecificationCharacteristic });
      comp.ngOnInit();

      expect(comp.productSpecificationCharacteristicRelationshipsSharedCollection).toContain(
        productSpecificationCharacteristicRelationship
      );
      expect(comp.productSpecificationCharacteristic).toEqual(productSpecificationCharacteristic);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristic>>();
      const productSpecificationCharacteristic = { id: 'ABC' };
      jest
        .spyOn(productSpecificationCharacteristicFormService, 'getProductSpecificationCharacteristic')
        .mockReturnValue(productSpecificationCharacteristic);
      jest.spyOn(productSpecificationCharacteristicService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationCharacteristic }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationCharacteristicFormService.getProductSpecificationCharacteristic).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSpecificationCharacteristicService.update).toHaveBeenCalledWith(
        expect.objectContaining(productSpecificationCharacteristic)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristic>>();
      const productSpecificationCharacteristic = { id: 'ABC' };
      jest.spyOn(productSpecificationCharacteristicFormService, 'getProductSpecificationCharacteristic').mockReturnValue({ id: null });
      jest.spyOn(productSpecificationCharacteristicService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristic: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationCharacteristic }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationCharacteristicFormService.getProductSpecificationCharacteristic).toHaveBeenCalled();
      expect(productSpecificationCharacteristicService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristic>>();
      const productSpecificationCharacteristic = { id: 'ABC' };
      jest.spyOn(productSpecificationCharacteristicService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSpecificationCharacteristicService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProductSpecificationCharacteristicRelationship', () => {
      it('Should forward to productSpecificationCharacteristicRelationshipService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(productSpecificationCharacteristicRelationshipService, 'compareProductSpecificationCharacteristicRelationship');
        comp.compareProductSpecificationCharacteristicRelationship(entity, entity2);
        expect(
          productSpecificationCharacteristicRelationshipService.compareProductSpecificationCharacteristicRelationship
        ).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
