import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSpecificationRelationshipFormService } from './product-specification-relationship-form.service';
import { ProductSpecificationRelationshipService } from '../service/product-specification-relationship.service';
import { IProductSpecificationRelationship } from '../product-specification-relationship.model';

import { ProductSpecificationRelationshipUpdateComponent } from './product-specification-relationship-update.component';

describe('ProductSpecificationRelationship Management Update Component', () => {
  let comp: ProductSpecificationRelationshipUpdateComponent;
  let fixture: ComponentFixture<ProductSpecificationRelationshipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSpecificationRelationshipFormService: ProductSpecificationRelationshipFormService;
  let productSpecificationRelationshipService: ProductSpecificationRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSpecificationRelationshipUpdateComponent],
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
      .overrideTemplate(ProductSpecificationRelationshipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSpecificationRelationshipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSpecificationRelationshipFormService = TestBed.inject(ProductSpecificationRelationshipFormService);
    productSpecificationRelationshipService = TestBed.inject(ProductSpecificationRelationshipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productSpecificationRelationship: IProductSpecificationRelationship = { id: 'CBA' };

      activatedRoute.data = of({ productSpecificationRelationship });
      comp.ngOnInit();

      expect(comp.productSpecificationRelationship).toEqual(productSpecificationRelationship);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationRelationship>>();
      const productSpecificationRelationship = { id: 'ABC' };
      jest
        .spyOn(productSpecificationRelationshipFormService, 'getProductSpecificationRelationship')
        .mockReturnValue(productSpecificationRelationship);
      jest.spyOn(productSpecificationRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationRelationship }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationRelationshipFormService.getProductSpecificationRelationship).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSpecificationRelationshipService.update).toHaveBeenCalledWith(
        expect.objectContaining(productSpecificationRelationship)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationRelationship>>();
      const productSpecificationRelationship = { id: 'ABC' };
      jest.spyOn(productSpecificationRelationshipFormService, 'getProductSpecificationRelationship').mockReturnValue({ id: null });
      jest.spyOn(productSpecificationRelationshipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationRelationship: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecificationRelationship }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationRelationshipFormService.getProductSpecificationRelationship).toHaveBeenCalled();
      expect(productSpecificationRelationshipService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecificationRelationship>>();
      const productSpecificationRelationship = { id: 'ABC' };
      jest.spyOn(productSpecificationRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecificationRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSpecificationRelationshipService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
