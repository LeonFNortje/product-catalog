import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../target-product-schema.test-samples';

import { TargetProductSchemaFormService } from './target-product-schema-form.service';

describe('TargetProductSchema Form Service', () => {
  let service: TargetProductSchemaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TargetProductSchemaFormService);
  });

  describe('Service methods', () => {
    describe('createTargetProductSchemaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTargetProductSchemaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing ITargetProductSchema should create a new form with FormGroup', () => {
        const formGroup = service.createTargetProductSchemaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getTargetProductSchema', () => {
      it('should return NewTargetProductSchema for default TargetProductSchema initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTargetProductSchemaFormGroup(sampleWithNewData);

        const targetProductSchema = service.getTargetProductSchema(formGroup) as any;

        expect(targetProductSchema).toMatchObject(sampleWithNewData);
      });

      it('should return NewTargetProductSchema for empty TargetProductSchema initial value', () => {
        const formGroup = service.createTargetProductSchemaFormGroup();

        const targetProductSchema = service.getTargetProductSchema(formGroup) as any;

        expect(targetProductSchema).toMatchObject({});
      });

      it('should return ITargetProductSchema', () => {
        const formGroup = service.createTargetProductSchemaFormGroup(sampleWithRequiredData);

        const targetProductSchema = service.getTargetProductSchema(formGroup) as any;

        expect(targetProductSchema).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITargetProductSchema should not enable id FormControl', () => {
        const formGroup = service.createTargetProductSchemaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTargetProductSchema should disable id FormControl', () => {
        const formGroup = service.createTargetProductSchemaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
