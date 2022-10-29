import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-specification-characteristic-relationship.test-samples';

import { ProductSpecificationCharacteristicRelationshipFormService } from './product-specification-characteristic-relationship-form.service';

describe('ProductSpecificationCharacteristicRelationship Form Service', () => {
  let service: ProductSpecificationCharacteristicRelationshipFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSpecificationCharacteristicRelationshipFormService);
  });

  describe('Service methods', () => {
    describe('createProductSpecificationCharacteristicRelationshipFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            relationshipType: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IProductSpecificationCharacteristicRelationship should create a new form with FormGroup', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            relationshipType: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getProductSpecificationCharacteristicRelationship', () => {
      it('should return NewProductSpecificationCharacteristicRelationship for default ProductSpecificationCharacteristicRelationship initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup(sampleWithNewData);

        const productSpecificationCharacteristicRelationship = service.getProductSpecificationCharacteristicRelationship(formGroup) as any;

        expect(productSpecificationCharacteristicRelationship).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSpecificationCharacteristicRelationship for empty ProductSpecificationCharacteristicRelationship initial value', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup();

        const productSpecificationCharacteristicRelationship = service.getProductSpecificationCharacteristicRelationship(formGroup) as any;

        expect(productSpecificationCharacteristicRelationship).toMatchObject({});
      });

      it('should return IProductSpecificationCharacteristicRelationship', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup(sampleWithRequiredData);

        const productSpecificationCharacteristicRelationship = service.getProductSpecificationCharacteristicRelationship(formGroup) as any;

        expect(productSpecificationCharacteristicRelationship).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSpecificationCharacteristicRelationship should not enable id FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSpecificationCharacteristicRelationship should disable id FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicRelationshipFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
