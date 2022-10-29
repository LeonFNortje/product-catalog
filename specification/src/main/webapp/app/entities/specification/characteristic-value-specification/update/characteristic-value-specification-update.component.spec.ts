import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CharacteristicValueSpecificationFormService } from './characteristic-value-specification-form.service';
import { CharacteristicValueSpecificationService } from '../service/characteristic-value-specification.service';
import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/specification/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from 'app/entities/specification/product-specification-characteristic-relationship/service/product-specification-characteristic-relationship.service';

import { CharacteristicValueSpecificationUpdateComponent } from './characteristic-value-specification-update.component';

describe('CharacteristicValueSpecification Management Update Component', () => {
  let comp: CharacteristicValueSpecificationUpdateComponent;
  let fixture: ComponentFixture<CharacteristicValueSpecificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let characteristicValueSpecificationFormService: CharacteristicValueSpecificationFormService;
  let characteristicValueSpecificationService: CharacteristicValueSpecificationService;
  let productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CharacteristicValueSpecificationUpdateComponent],
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
      .overrideTemplate(CharacteristicValueSpecificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CharacteristicValueSpecificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    characteristicValueSpecificationFormService = TestBed.inject(CharacteristicValueSpecificationFormService);
    characteristicValueSpecificationService = TestBed.inject(CharacteristicValueSpecificationService);
    productSpecificationCharacteristicRelationshipService = TestBed.inject(ProductSpecificationCharacteristicRelationshipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductSpecificationCharacteristicRelationship query and add missing value', () => {
      const characteristicValueSpecification: ICharacteristicValueSpecification = { id: 456 };
      const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = {
        id: 'c462cc44-10f9-44bc-8631-a90cf9d553c7',
      };
      characteristicValueSpecification.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;

      const productSpecificationCharacteristicRelationshipCollection: IProductSpecificationCharacteristicRelationship[] = [
        { id: 'ca1dba2d-ddec-4cf6-82fb-b7f2648bfcb9' },
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

      activatedRoute.data = of({ characteristicValueSpecification });
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
      const characteristicValueSpecification: ICharacteristicValueSpecification = { id: 456 };
      const productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship = {
        id: '96044bc0-be41-479f-b9b6-abe446878d19',
      };
      characteristicValueSpecification.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;

      activatedRoute.data = of({ characteristicValueSpecification });
      comp.ngOnInit();

      expect(comp.productSpecificationCharacteristicRelationshipsSharedCollection).toContain(
        productSpecificationCharacteristicRelationship
      );
      expect(comp.characteristicValueSpecification).toEqual(characteristicValueSpecification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacteristicValueSpecification>>();
      const characteristicValueSpecification = { id: 123 };
      jest
        .spyOn(characteristicValueSpecificationFormService, 'getCharacteristicValueSpecification')
        .mockReturnValue(characteristicValueSpecification);
      jest.spyOn(characteristicValueSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characteristicValueSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: characteristicValueSpecification }));
      saveSubject.complete();

      // THEN
      expect(characteristicValueSpecificationFormService.getCharacteristicValueSpecification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(characteristicValueSpecificationService.update).toHaveBeenCalledWith(
        expect.objectContaining(characteristicValueSpecification)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacteristicValueSpecification>>();
      const characteristicValueSpecification = { id: 123 };
      jest.spyOn(characteristicValueSpecificationFormService, 'getCharacteristicValueSpecification').mockReturnValue({ id: null });
      jest.spyOn(characteristicValueSpecificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characteristicValueSpecification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: characteristicValueSpecification }));
      saveSubject.complete();

      // THEN
      expect(characteristicValueSpecificationFormService.getCharacteristicValueSpecification).toHaveBeenCalled();
      expect(characteristicValueSpecificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICharacteristicValueSpecification>>();
      const characteristicValueSpecification = { id: 123 };
      jest.spyOn(characteristicValueSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ characteristicValueSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(characteristicValueSpecificationService.update).toHaveBeenCalled();
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
