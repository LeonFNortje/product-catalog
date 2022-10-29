import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../service-specification-ref.test-samples';

import { ServiceSpecificationRefFormService } from './service-specification-ref-form.service';

describe('ServiceSpecificationRef Form Service', () => {
  let service: ServiceSpecificationRefFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceSpecificationRefFormService);
  });

  describe('Service methods', () => {
    describe('createServiceSpecificationRefFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            version: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IServiceSpecificationRef should create a new form with FormGroup', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            version: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getServiceSpecificationRef', () => {
      it('should return NewServiceSpecificationRef for default ServiceSpecificationRef initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createServiceSpecificationRefFormGroup(sampleWithNewData);

        const serviceSpecificationRef = service.getServiceSpecificationRef(formGroup) as any;

        expect(serviceSpecificationRef).toMatchObject(sampleWithNewData);
      });

      it('should return NewServiceSpecificationRef for empty ServiceSpecificationRef initial value', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup();

        const serviceSpecificationRef = service.getServiceSpecificationRef(formGroup) as any;

        expect(serviceSpecificationRef).toMatchObject({});
      });

      it('should return IServiceSpecificationRef', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup(sampleWithRequiredData);

        const serviceSpecificationRef = service.getServiceSpecificationRef(formGroup) as any;

        expect(serviceSpecificationRef).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServiceSpecificationRef should not enable id FormControl', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServiceSpecificationRef should disable id FormControl', () => {
        const formGroup = service.createServiceSpecificationRefFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
