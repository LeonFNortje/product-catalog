import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-specification-relationship.test-samples';

import { ProductSpecificationRelationshipFormService } from './product-specification-relationship-form.service';

describe('ProductSpecificationRelationship Form Service', () => {
  let service: ProductSpecificationRelationshipFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSpecificationRelationshipFormService);
  });

  describe('Service methods', () => {
    describe('createProductSpecificationRelationshipFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup();

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

      it('passing IProductSpecificationRelationship should create a new form with FormGroup', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup(sampleWithRequiredData);

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

    describe('getProductSpecificationRelationship', () => {
      it('should return NewProductSpecificationRelationship for default ProductSpecificationRelationship initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSpecificationRelationshipFormGroup(sampleWithNewData);

        const productSpecificationRelationship = service.getProductSpecificationRelationship(formGroup) as any;

        expect(productSpecificationRelationship).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSpecificationRelationship for empty ProductSpecificationRelationship initial value', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup();

        const productSpecificationRelationship = service.getProductSpecificationRelationship(formGroup) as any;

        expect(productSpecificationRelationship).toMatchObject({});
      });

      it('should return IProductSpecificationRelationship', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup(sampleWithRequiredData);

        const productSpecificationRelationship = service.getProductSpecificationRelationship(formGroup) as any;

        expect(productSpecificationRelationship).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSpecificationRelationship should not enable id FormControl', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSpecificationRelationship should disable id FormControl', () => {
        const formGroup = service.createProductSpecificationRelationshipFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
