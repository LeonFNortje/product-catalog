import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-specification-characteristic.test-samples';

import { ProductSpecificationCharacteristicFormService } from './product-specification-characteristic-form.service';

describe('ProductSpecificationCharacteristic Form Service', () => {
  let service: ProductSpecificationCharacteristicFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSpecificationCharacteristicFormService);
  });

  describe('Service methods', () => {
    describe('createProductSpecificationCharacteristicFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            configurable: expect.any(Object),
            description: expect.any(Object),
            extensible: expect.any(Object),
            id: expect.any(Object),
            isUnique: expect.any(Object),
            maxCardinality: expect.any(Object),
            minCardinality: expect.any(Object),
            name: expect.any(Object),
            regex: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            valueSchemaLocation: expect.any(Object),
            productSpecificationCharacteristicRelationship: expect.any(Object),
          })
        );
      });

      it('passing IProductSpecificationCharacteristic should create a new form with FormGroup', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            configurable: expect.any(Object),
            description: expect.any(Object),
            extensible: expect.any(Object),
            id: expect.any(Object),
            isUnique: expect.any(Object),
            maxCardinality: expect.any(Object),
            minCardinality: expect.any(Object),
            name: expect.any(Object),
            regex: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            valueSchemaLocation: expect.any(Object),
            productSpecificationCharacteristicRelationship: expect.any(Object),
          })
        );
      });
    });

    describe('getProductSpecificationCharacteristic', () => {
      it('should return NewProductSpecificationCharacteristic for default ProductSpecificationCharacteristic initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSpecificationCharacteristicFormGroup(sampleWithNewData);

        const productSpecificationCharacteristic = service.getProductSpecificationCharacteristic(formGroup) as any;

        expect(productSpecificationCharacteristic).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSpecificationCharacteristic for empty ProductSpecificationCharacteristic initial value', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup();

        const productSpecificationCharacteristic = service.getProductSpecificationCharacteristic(formGroup) as any;

        expect(productSpecificationCharacteristic).toMatchObject({});
      });

      it('should return IProductSpecificationCharacteristic', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup(sampleWithRequiredData);

        const productSpecificationCharacteristic = service.getProductSpecificationCharacteristic(formGroup) as any;

        expect(productSpecificationCharacteristic).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSpecificationCharacteristic should not enable id FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSpecificationCharacteristic should disable id FormControl', () => {
        const formGroup = service.createProductSpecificationCharacteristicFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
