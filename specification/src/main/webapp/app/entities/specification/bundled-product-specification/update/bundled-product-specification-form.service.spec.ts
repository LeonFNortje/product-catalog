import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bundled-product-specification.test-samples';

import { BundledProductSpecificationFormService } from './bundled-product-specification-form.service';

describe('BundledProductSpecification Form Service', () => {
  let service: BundledProductSpecificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BundledProductSpecificationFormService);
  });

  describe('Service methods', () => {
    describe('createBundledProductSpecificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            lifecycleStatus: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IBundledProductSpecification should create a new form with FormGroup', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            lifecycleStatus: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getBundledProductSpecification', () => {
      it('should return NewBundledProductSpecification for default BundledProductSpecification initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBundledProductSpecificationFormGroup(sampleWithNewData);

        const bundledProductSpecification = service.getBundledProductSpecification(formGroup) as any;

        expect(bundledProductSpecification).toMatchObject(sampleWithNewData);
      });

      it('should return NewBundledProductSpecification for empty BundledProductSpecification initial value', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup();

        const bundledProductSpecification = service.getBundledProductSpecification(formGroup) as any;

        expect(bundledProductSpecification).toMatchObject({});
      });

      it('should return IBundledProductSpecification', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup(sampleWithRequiredData);

        const bundledProductSpecification = service.getBundledProductSpecification(formGroup) as any;

        expect(bundledProductSpecification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBundledProductSpecification should not enable id FormControl', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBundledProductSpecification should disable id FormControl', () => {
        const formGroup = service.createBundledProductSpecificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
