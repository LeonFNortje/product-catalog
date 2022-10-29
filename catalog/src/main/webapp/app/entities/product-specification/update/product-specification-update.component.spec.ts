import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSpecificationFormService } from './product-specification-form.service';
import { ProductSpecificationService } from '../service/product-specification.service';
import { IProductSpecification } from '../product-specification.model';
import { ITargetProductSchema } from 'app/entities/target-product-schema/target-product-schema.model';
import { TargetProductSchemaService } from 'app/entities/target-product-schema/service/target-product-schema.service';
import { IResourceSpecificationRef } from 'app/entities/resource-specification-ref/resource-specification-ref.model';
import { ResourceSpecificationRefService } from 'app/entities/resource-specification-ref/service/resource-specification-ref.service';
import { IAttachmentRefOrValue } from 'app/entities/attachment-ref-or-value/attachment-ref-or-value.model';
import { AttachmentRefOrValueService } from 'app/entities/attachment-ref-or-value/service/attachment-ref-or-value.service';
import { IRelatedParty } from 'app/entities/related-party/related-party.model';
import { RelatedPartyService } from 'app/entities/related-party/service/related-party.service';
import { IServiceSpecificationRef } from 'app/entities/service-specification-ref/service-specification-ref.model';
import { ServiceSpecificationRefService } from 'app/entities/service-specification-ref/service/service-specification-ref.service';
import { IProductSpecificationRelationship } from 'app/entities/product-specification-relationship/product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from 'app/entities/product-specification-relationship/service/product-specification-relationship.service';
import { IBundledProductSpecification } from 'app/entities/bundled-product-specification/bundled-product-specification.model';
import { BundledProductSpecificationService } from 'app/entities/bundled-product-specification/service/bundled-product-specification.service';
import { IProductSpecificationCharacteristic } from 'app/entities/product-specification-characteristic/product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from 'app/entities/product-specification-characteristic/service/product-specification-characteristic.service';

import { ProductSpecificationUpdateComponent } from './product-specification-update.component';

describe('ProductSpecification Management Update Component', () => {
  let comp: ProductSpecificationUpdateComponent;
  let fixture: ComponentFixture<ProductSpecificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSpecificationFormService: ProductSpecificationFormService;
  let productSpecificationService: ProductSpecificationService;
  let targetProductSchemaService: TargetProductSchemaService;
  let resourceSpecificationRefService: ResourceSpecificationRefService;
  let attachmentRefOrValueService: AttachmentRefOrValueService;
  let relatedPartyService: RelatedPartyService;
  let serviceSpecificationRefService: ServiceSpecificationRefService;
  let productSpecificationRelationshipService: ProductSpecificationRelationshipService;
  let bundledProductSpecificationService: BundledProductSpecificationService;
  let productSpecificationCharacteristicService: ProductSpecificationCharacteristicService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSpecificationUpdateComponent],
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
      .overrideTemplate(ProductSpecificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSpecificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSpecificationFormService = TestBed.inject(ProductSpecificationFormService);
    productSpecificationService = TestBed.inject(ProductSpecificationService);
    targetProductSchemaService = TestBed.inject(TargetProductSchemaService);
    resourceSpecificationRefService = TestBed.inject(ResourceSpecificationRefService);
    attachmentRefOrValueService = TestBed.inject(AttachmentRefOrValueService);
    relatedPartyService = TestBed.inject(RelatedPartyService);
    serviceSpecificationRefService = TestBed.inject(ServiceSpecificationRefService);
    productSpecificationRelationshipService = TestBed.inject(ProductSpecificationRelationshipService);
    bundledProductSpecificationService = TestBed.inject(BundledProductSpecificationService);
    productSpecificationCharacteristicService = TestBed.inject(ProductSpecificationCharacteristicService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TargetProductSchema query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const targetProductSchema: ITargetProductSchema = { id: 20852 };
      productSpecification.targetProductSchema = targetProductSchema;

      const targetProductSchemaCollection: ITargetProductSchema[] = [{ id: 85198 }];
      jest.spyOn(targetProductSchemaService, 'query').mockReturnValue(of(new HttpResponse({ body: targetProductSchemaCollection })));
      const additionalTargetProductSchemas = [targetProductSchema];
      const expectedCollection: ITargetProductSchema[] = [...additionalTargetProductSchemas, ...targetProductSchemaCollection];
      jest.spyOn(targetProductSchemaService, 'addTargetProductSchemaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(targetProductSchemaService.query).toHaveBeenCalled();
      expect(targetProductSchemaService.addTargetProductSchemaToCollectionIfMissing).toHaveBeenCalledWith(
        targetProductSchemaCollection,
        ...additionalTargetProductSchemas.map(expect.objectContaining)
      );
      expect(comp.targetProductSchemasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResourceSpecificationRef query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const resourceSpecificationRef: IResourceSpecificationRef = { id: '56cb6d46-a4c4-4b42-a8bc-46eef62cfcd9' };
      productSpecification.resourceSpecificationRef = resourceSpecificationRef;

      const resourceSpecificationRefCollection: IResourceSpecificationRef[] = [{ id: 'd4b9ace5-fe58-4ad7-b826-c04fe05b33f1' }];
      jest
        .spyOn(resourceSpecificationRefService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: resourceSpecificationRefCollection })));
      const additionalResourceSpecificationRefs = [resourceSpecificationRef];
      const expectedCollection: IResourceSpecificationRef[] = [
        ...additionalResourceSpecificationRefs,
        ...resourceSpecificationRefCollection,
      ];
      jest.spyOn(resourceSpecificationRefService, 'addResourceSpecificationRefToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(resourceSpecificationRefService.query).toHaveBeenCalled();
      expect(resourceSpecificationRefService.addResourceSpecificationRefToCollectionIfMissing).toHaveBeenCalledWith(
        resourceSpecificationRefCollection,
        ...additionalResourceSpecificationRefs.map(expect.objectContaining)
      );
      expect(comp.resourceSpecificationRefsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AttachmentRefOrValue query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const attachmentRefOrValue: IAttachmentRefOrValue = { id: '7cba24df-4739-46fa-a9bb-52ac27ccc798' };
      productSpecification.attachmentRefOrValue = attachmentRefOrValue;

      const attachmentRefOrValueCollection: IAttachmentRefOrValue[] = [{ id: '7960075e-a339-45d9-87ad-41a4cc3cd2e5' }];
      jest.spyOn(attachmentRefOrValueService, 'query').mockReturnValue(of(new HttpResponse({ body: attachmentRefOrValueCollection })));
      const additionalAttachmentRefOrValues = [attachmentRefOrValue];
      const expectedCollection: IAttachmentRefOrValue[] = [...additionalAttachmentRefOrValues, ...attachmentRefOrValueCollection];
      jest.spyOn(attachmentRefOrValueService, 'addAttachmentRefOrValueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(attachmentRefOrValueService.query).toHaveBeenCalled();
      expect(attachmentRefOrValueService.addAttachmentRefOrValueToCollectionIfMissing).toHaveBeenCalledWith(
        attachmentRefOrValueCollection,
        ...additionalAttachmentRefOrValues.map(expect.objectContaining)
      );
      expect(comp.attachmentRefOrValuesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RelatedParty query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const relatedParty: IRelatedParty = { id: '1580b6c8-f98e-4637-b501-930bbe1660f5' };
      productSpecification.relatedParty = relatedParty;

      const relatedPartyCollection: IRelatedParty[] = [{ id: 'b0ff35c0-f3a8-4947-b7bd-c0a61cd11f3f' }];
      jest.spyOn(relatedPartyService, 'query').mockReturnValue(of(new HttpResponse({ body: relatedPartyCollection })));
      const additionalRelatedParties = [relatedParty];
      const expectedCollection: IRelatedParty[] = [...additionalRelatedParties, ...relatedPartyCollection];
      jest.spyOn(relatedPartyService, 'addRelatedPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(relatedPartyService.query).toHaveBeenCalled();
      expect(relatedPartyService.addRelatedPartyToCollectionIfMissing).toHaveBeenCalledWith(
        relatedPartyCollection,
        ...additionalRelatedParties.map(expect.objectContaining)
      );
      expect(comp.relatedPartiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ServiceSpecificationRef query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const serviceSpecificationRef: IServiceSpecificationRef = { id: '3bfa4df2-d3c3-4b0a-adce-1d693d62235c' };
      productSpecification.serviceSpecificationRef = serviceSpecificationRef;

      const serviceSpecificationRefCollection: IServiceSpecificationRef[] = [{ id: '1077c06d-78b5-4f81-bbd3-b96c9561bcdf' }];
      jest
        .spyOn(serviceSpecificationRefService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: serviceSpecificationRefCollection })));
      const additionalServiceSpecificationRefs = [serviceSpecificationRef];
      const expectedCollection: IServiceSpecificationRef[] = [...additionalServiceSpecificationRefs, ...serviceSpecificationRefCollection];
      jest.spyOn(serviceSpecificationRefService, 'addServiceSpecificationRefToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(serviceSpecificationRefService.query).toHaveBeenCalled();
      expect(serviceSpecificationRefService.addServiceSpecificationRefToCollectionIfMissing).toHaveBeenCalledWith(
        serviceSpecificationRefCollection,
        ...additionalServiceSpecificationRefs.map(expect.objectContaining)
      );
      expect(comp.serviceSpecificationRefsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProductSpecificationRelationship query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const productSpecificationRelationship: IProductSpecificationRelationship = { id: '3bc5547f-6de4-4dc8-b89d-90cb5122abf1' };
      productSpecification.productSpecificationRelationship = productSpecificationRelationship;

      const productSpecificationRelationshipCollection: IProductSpecificationRelationship[] = [
        { id: '9ef100fe-a32e-4935-9d21-efa3159224b0' },
      ];
      jest
        .spyOn(productSpecificationRelationshipService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: productSpecificationRelationshipCollection })));
      const additionalProductSpecificationRelationships = [productSpecificationRelationship];
      const expectedCollection: IProductSpecificationRelationship[] = [
        ...additionalProductSpecificationRelationships,
        ...productSpecificationRelationshipCollection,
      ];
      jest
        .spyOn(productSpecificationRelationshipService, 'addProductSpecificationRelationshipToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(productSpecificationRelationshipService.query).toHaveBeenCalled();
      expect(productSpecificationRelationshipService.addProductSpecificationRelationshipToCollectionIfMissing).toHaveBeenCalledWith(
        productSpecificationRelationshipCollection,
        ...additionalProductSpecificationRelationships.map(expect.objectContaining)
      );
      expect(comp.productSpecificationRelationshipsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BundledProductSpecification query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const bundledProductSpecification: IBundledProductSpecification = { id: '5a715b86-fda5-45a9-8ffc-6cb47b96d7f4' };
      productSpecification.bundledProductSpecification = bundledProductSpecification;

      const bundledProductSpecificationCollection: IBundledProductSpecification[] = [{ id: 'ed5ebf62-5f93-4519-9eac-849efa16a680' }];
      jest
        .spyOn(bundledProductSpecificationService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: bundledProductSpecificationCollection })));
      const additionalBundledProductSpecifications = [bundledProductSpecification];
      const expectedCollection: IBundledProductSpecification[] = [
        ...additionalBundledProductSpecifications,
        ...bundledProductSpecificationCollection,
      ];
      jest
        .spyOn(bundledProductSpecificationService, 'addBundledProductSpecificationToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(bundledProductSpecificationService.query).toHaveBeenCalled();
      expect(bundledProductSpecificationService.addBundledProductSpecificationToCollectionIfMissing).toHaveBeenCalledWith(
        bundledProductSpecificationCollection,
        ...additionalBundledProductSpecifications.map(expect.objectContaining)
      );
      expect(comp.bundledProductSpecificationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProductSpecificationCharacteristic query and add missing value', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const productSpecificationCharacteristic: IProductSpecificationCharacteristic = { id: '9156db64-ee40-44da-a4d4-d469bf0e3c3b' };
      productSpecification.productSpecificationCharacteristic = productSpecificationCharacteristic;

      const productSpecificationCharacteristicCollection: IProductSpecificationCharacteristic[] = [
        { id: 'f358d786-5e37-4d74-9137-b0931a136264' },
      ];
      jest
        .spyOn(productSpecificationCharacteristicService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: productSpecificationCharacteristicCollection })));
      const additionalProductSpecificationCharacteristics = [productSpecificationCharacteristic];
      const expectedCollection: IProductSpecificationCharacteristic[] = [
        ...additionalProductSpecificationCharacteristics,
        ...productSpecificationCharacteristicCollection,
      ];
      jest
        .spyOn(productSpecificationCharacteristicService, 'addProductSpecificationCharacteristicToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(productSpecificationCharacteristicService.query).toHaveBeenCalled();
      expect(productSpecificationCharacteristicService.addProductSpecificationCharacteristicToCollectionIfMissing).toHaveBeenCalledWith(
        productSpecificationCharacteristicCollection,
        ...additionalProductSpecificationCharacteristics.map(expect.objectContaining)
      );
      expect(comp.productSpecificationCharacteristicsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productSpecification: IProductSpecification = { id: 'CBA' };
      const targetProductSchema: ITargetProductSchema = { id: 20331 };
      productSpecification.targetProductSchema = targetProductSchema;
      const resourceSpecificationRef: IResourceSpecificationRef = { id: 'b833ced5-7ec0-4487-a787-c321d3af4913' };
      productSpecification.resourceSpecificationRef = resourceSpecificationRef;
      const attachmentRefOrValue: IAttachmentRefOrValue = { id: '1513bc4c-f49b-4f19-b47c-9c5cc52f43f9' };
      productSpecification.attachmentRefOrValue = attachmentRefOrValue;
      const relatedParty: IRelatedParty = { id: '8556c655-8347-4791-a88e-8c65d1ea9db8' };
      productSpecification.relatedParty = relatedParty;
      const serviceSpecificationRef: IServiceSpecificationRef = { id: 'c12e63c1-296d-4e1e-b6a1-3143982b8ef1' };
      productSpecification.serviceSpecificationRef = serviceSpecificationRef;
      const productSpecificationRelationship: IProductSpecificationRelationship = { id: '5ca90417-96ff-4a07-878b-0345593d4fc8' };
      productSpecification.productSpecificationRelationship = productSpecificationRelationship;
      const bundledProductSpecification: IBundledProductSpecification = { id: '6901dcd0-06f3-4ec4-b475-7ea1b6efad23' };
      productSpecification.bundledProductSpecification = bundledProductSpecification;
      const productSpecificationCharacteristic: IProductSpecificationCharacteristic = { id: 'f65d8c6a-422c-4597-9dfb-87ecbc807319' };
      productSpecification.productSpecificationCharacteristic = productSpecificationCharacteristic;

      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      expect(comp.targetProductSchemasSharedCollection).toContain(targetProductSchema);
      expect(comp.resourceSpecificationRefsSharedCollection).toContain(resourceSpecificationRef);
      expect(comp.attachmentRefOrValuesSharedCollection).toContain(attachmentRefOrValue);
      expect(comp.relatedPartiesSharedCollection).toContain(relatedParty);
      expect(comp.serviceSpecificationRefsSharedCollection).toContain(serviceSpecificationRef);
      expect(comp.productSpecificationRelationshipsSharedCollection).toContain(productSpecificationRelationship);
      expect(comp.bundledProductSpecificationsSharedCollection).toContain(bundledProductSpecification);
      expect(comp.productSpecificationCharacteristicsSharedCollection).toContain(productSpecificationCharacteristic);
      expect(comp.productSpecification).toEqual(productSpecification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecification>>();
      const productSpecification = { id: 'ABC' };
      jest.spyOn(productSpecificationFormService, 'getProductSpecification').mockReturnValue(productSpecification);
      jest.spyOn(productSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecification }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationFormService.getProductSpecification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSpecificationService.update).toHaveBeenCalledWith(expect.objectContaining(productSpecification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecification>>();
      const productSpecification = { id: 'ABC' };
      jest.spyOn(productSpecificationFormService, 'getProductSpecification').mockReturnValue({ id: null });
      jest.spyOn(productSpecificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSpecification }));
      saveSubject.complete();

      // THEN
      expect(productSpecificationFormService.getProductSpecification).toHaveBeenCalled();
      expect(productSpecificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSpecification>>();
      const productSpecification = { id: 'ABC' };
      jest.spyOn(productSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSpecificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTargetProductSchema', () => {
      it('Should forward to targetProductSchemaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(targetProductSchemaService, 'compareTargetProductSchema');
        comp.compareTargetProductSchema(entity, entity2);
        expect(targetProductSchemaService.compareTargetProductSchema).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResourceSpecificationRef', () => {
      it('Should forward to resourceSpecificationRefService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(resourceSpecificationRefService, 'compareResourceSpecificationRef');
        comp.compareResourceSpecificationRef(entity, entity2);
        expect(resourceSpecificationRefService.compareResourceSpecificationRef).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAttachmentRefOrValue', () => {
      it('Should forward to attachmentRefOrValueService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(attachmentRefOrValueService, 'compareAttachmentRefOrValue');
        comp.compareAttachmentRefOrValue(entity, entity2);
        expect(attachmentRefOrValueService.compareAttachmentRefOrValue).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRelatedParty', () => {
      it('Should forward to relatedPartyService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(relatedPartyService, 'compareRelatedParty');
        comp.compareRelatedParty(entity, entity2);
        expect(relatedPartyService.compareRelatedParty).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServiceSpecificationRef', () => {
      it('Should forward to serviceSpecificationRefService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(serviceSpecificationRefService, 'compareServiceSpecificationRef');
        comp.compareServiceSpecificationRef(entity, entity2);
        expect(serviceSpecificationRefService.compareServiceSpecificationRef).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProductSpecificationRelationship', () => {
      it('Should forward to productSpecificationRelationshipService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(productSpecificationRelationshipService, 'compareProductSpecificationRelationship');
        comp.compareProductSpecificationRelationship(entity, entity2);
        expect(productSpecificationRelationshipService.compareProductSpecificationRelationship).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBundledProductSpecification', () => {
      it('Should forward to bundledProductSpecificationService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(bundledProductSpecificationService, 'compareBundledProductSpecification');
        comp.compareBundledProductSpecification(entity, entity2);
        expect(bundledProductSpecificationService.compareBundledProductSpecification).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProductSpecificationCharacteristic', () => {
      it('Should forward to productSpecificationCharacteristicService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(productSpecificationCharacteristicService, 'compareProductSpecificationCharacteristic');
        comp.compareProductSpecificationCharacteristic(entity, entity2);
        expect(productSpecificationCharacteristicService.compareProductSpecificationCharacteristic).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
