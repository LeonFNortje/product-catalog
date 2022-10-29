import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../resource-specification-ref.test-samples';

import { ResourceSpecificationRefFormService } from './resource-specification-ref-form.service';

describe('ResourceSpecificationRef Form Service', () => {
  let service: ResourceSpecificationRefFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResourceSpecificationRefFormService);
  });

  describe('Service methods', () => {
    describe('createResourceSpecificationRefFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup();

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

      it('passing IResourceSpecificationRef should create a new form with FormGroup', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup(sampleWithRequiredData);

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

    describe('getResourceSpecificationRef', () => {
      it('should return NewResourceSpecificationRef for default ResourceSpecificationRef initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResourceSpecificationRefFormGroup(sampleWithNewData);

        const resourceSpecificationRef = service.getResourceSpecificationRef(formGroup) as any;

        expect(resourceSpecificationRef).toMatchObject(sampleWithNewData);
      });

      it('should return NewResourceSpecificationRef for empty ResourceSpecificationRef initial value', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup();

        const resourceSpecificationRef = service.getResourceSpecificationRef(formGroup) as any;

        expect(resourceSpecificationRef).toMatchObject({});
      });

      it('should return IResourceSpecificationRef', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup(sampleWithRequiredData);

        const resourceSpecificationRef = service.getResourceSpecificationRef(formGroup) as any;

        expect(resourceSpecificationRef).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResourceSpecificationRef should not enable id FormControl', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResourceSpecificationRef should disable id FormControl', () => {
        const formGroup = service.createResourceSpecificationRefFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
