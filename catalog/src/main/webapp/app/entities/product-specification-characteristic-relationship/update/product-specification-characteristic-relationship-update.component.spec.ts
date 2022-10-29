import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSpecificationCharacteristicRelationshipFormService } from './product-specification-characteristic-relationship-form.service';
import { ProductSpecificationCharacteristicRelationshipService } from '../service/product-specification-characteristic-relationship.service';
import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';

import { ProductSpecificationCharacteristicRelationshipUpdateComponent } from './product-specification-characteristic-relationship-update.component';

describe('ProductSpecificationCharacteristicRelationship Management Update Component', () => {
  let comp: ProductSpecificationCharacteristicRelationshipUpdateComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicRelationshipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSpecificationCharacteristicRelationshipFormService: ProductSpecificationCharacteristicRelationshipFormService;
  let productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSpecificationCharacteristicRelationshipUpdateComponent],
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
      .overrideTemplate(ProductSpecificationCharacteristicRelationshipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSpecificationCharacteristicRelationshipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSpecificationCharacteristicRelationshipFormService = TestBed.inject(ProductSpecificationCharacteristicRelationshipFormService);
    productSpecificationCharacteristicRelationshipService = TestBed.inject(ProductSpecificationCharacteristicRelationshipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = { id: 'CBA' };

      activatedRoute.data = of({ productSpecificationCharacteristicRelationship });
      comp.ngOnInit();

      expect(comp.productSpecificationCharacteristicRelationship).toEqual(productSpecificationCharacteristicRelationship);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristicRelationship>>();
      const productSpecificationCharacteristicRelationship = { id: 'ABC' };
      jest
        .spyOn(productSpecificationCharacteristicRelationshipFormService, 'getProductSpecificationCharacteristicRelationship')
        .mockReturnValue(productSpecificationCharacteristicRelationship);
      jest.spyOn(productSpecificationCharacteristicRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristicRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationCharacteristicRelationship }));
      saveSubject.complete();

      // THEN
      expect(
        productSpecificationCharacteristicRelationshipFormService.getProductSpecificationCharacteristicRelationship
      ).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSpecificationCharacteristicRelationshipService.update).toHaveBeenCalledWith(
        expect.objectContaining(productSpecificationCharacteristicRelationship)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristicRelationship>>();
      const productSpecificationCharacteristicRelationship = { id: 'ABC' };
      jest
        .spyOn(productSpecificationCharacteristicRelationshipFormService, 'getProductSpecificationCharacteristicRelationship')
        .mockReturnValue({ id: null });
      jest.spyOn(productSpecificationCharacteristicRelationshipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristicRelationship: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationCharacteristicRelationship }));
      saveSubject.complete();

      // THEN
      expect(
        productSpecificationCharacteristicRelationshipFormService.getProductSpecificationCharacteristicRelationship
      ).toHaveBeenCalled();
      expect(productSpecificationCharacteristicRelationshipService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationCharacteristicRelationship>>();
      const productSpecificationCharacteristicRelationship = { id: 'ABC' };
      jest.spyOn(productSpecificationCharacteristicRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationCharacteristicRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSpecificationCharacteristicRelationshipService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
