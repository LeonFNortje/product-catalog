import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../attachment-ref-or-value.test-samples';

import { AttachmentRefOrValueFormService } from './attachment-ref-or-value-form.service';

describe('AttachmentRefOrValue Form Service', () => {
  let service: AttachmentRefOrValueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttachmentRefOrValueFormService);
  });

  describe('Service methods', () => {
    describe('createAttachmentRefOrValueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachmentType: expect.any(Object),
            content: expect.any(Object),
            description: expect.any(Object),
            href: expect.any(Object),
            id: expect.any(Object),
            mimeType: expect.any(Object),
            name: expect.any(Object),
            sizeOfBytes: expect.any(Object),
            url: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IAttachmentRefOrValue should create a new form with FormGroup', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachmentType: expect.any(Object),
            content: expect.any(Object),
            description: expect.any(Object),
            href: expect.any(Object),
            id: expect.any(Object),
            mimeType: expect.any(Object),
            name: expect.any(Object),
            sizeOfBytes: expect.any(Object),
            url: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getAttachmentRefOrValue', () => {
      it('should return NewAttachmentRefOrValue for default AttachmentRefOrValue initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAttachmentRefOrValueFormGroup(sampleWithNewData);

        const attachmentRefOrValue = service.getAttachmentRefOrValue(formGroup) as any;

        expect(attachmentRefOrValue).toMatchObject(sampleWithNewData);
      });

      it('should return NewAttachmentRefOrValue for empty AttachmentRefOrValue initial value', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup();

        const attachmentRefOrValue = service.getAttachmentRefOrValue(formGroup) as any;

        expect(attachmentRefOrValue).toMatchObject({});
      });

      it('should return IAttachmentRefOrValue', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup(sampleWithRequiredData);

        const attachmentRefOrValue = service.getAttachmentRefOrValue(formGroup) as any;

        expect(attachmentRefOrValue).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAttachmentRefOrValue should not enable id FormControl', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAttachmentRefOrValue should disable id FormControl', () => {
        const formGroup = service.createAttachmentRefOrValueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
