import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-specification.test-samples';

import { ProductSpecificationFormService } from './product-specification-form.service';

describe('ProductSpecification Form Service', () => {
  let service: ProductSpecificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSpecificationFormService);
  });

  describe('Service methods', () => {
    describe('createProductSpecificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSpecificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            brand: expect.any(Object),
            description: expect.any(Object),
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            isBundle: expect.any(Object),
            lastUpdate: expect.any(Object),
            lifecycleStatus: expect.any(Object),
            productNumber: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            version: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            targetProductSchema: expect.any(Object),
            resourceSpecificationRef: expect.any(Object),
            attachmentRefOrValue: expect.any(Object),
            relatedParty: expect.any(Object),
            serviceSpecificationRef: expect.any(Object),
            productSpecificationRelationship: expect.any(Object),
            bundledProductSpecification: expect.any(Object),
            productSpecificationCharacteristic: expect.any(Object),
          })
        );
      });

      it('passing IProductSpecification should create a new form with FormGroup', () => {
        const formGroup = service.createProductSpecificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            brand: expect.any(Object),
            description: expect.any(Object),
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            isBundle: expect.any(Object),
            lastUpdate: expect.any(Object),
            lifecycleStatus: expect.any(Object),
            productNumber: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            version: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            targetProductSchema: expect.any(Object),
            resourceSpecificationRef: expect.any(Object),
            attachmentRefOrValue: expect.any(Object),
            relatedParty: expect.any(Object),
            serviceSpecificationRef: expect.any(Object),
            productSpecificationRelationship: expect.any(Object),
            bundledProductSpecification: expect.any(Object),
            productSpecificationCharacteristic: expect.any(Object),
          })
        );
      });
    });

    describe('getProductSpecification', () => {
      it('should return NewProductSpecification for default ProductSpecification initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSpecificationFormGroup(sampleWithNewData);

        const productSpecification = service.getProductSpecification(formGroup) as any;

        expect(productSpecification).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSpecification for empty ProductSpecification initial value', () => {
        const formGroup = service.createProductSpecificationFormGroup();

        const productSpecification = service.getProductSpecification(formGroup) as any;

        expect(productSpecification).toMatchObject({});
      });

      it('should return IProductSpecification', () => {
        const formGroup = service.createProductSpecificationFormGroup(sampleWithRequiredData);

        const productSpecification = service.getProductSpecification(formGroup) as any;

        expect(productSpecification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSpecification should not enable id FormControl', () => {
        const formGroup = service.createProductSpecificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSpecification should disable id FormControl', () => {
        const formGroup = service.createProductSpecificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
