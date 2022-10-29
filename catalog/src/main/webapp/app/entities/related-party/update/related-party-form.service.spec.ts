import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../related-party.test-samples';

import { RelatedPartyFormService } from './related-party-form.service';

describe('RelatedParty Form Service', () => {
  let service: RelatedPartyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RelatedPartyFormService);
  });

  describe('Service methods', () => {
    describe('createRelatedPartyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRelatedPartyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            role: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IRelatedParty should create a new form with FormGroup', () => {
        const formGroup = service.createRelatedPartyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            href: expect.any(Object),
            id: expect.any(Object),
            name: expect.any(Object),
            role: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getRelatedParty', () => {
      it('should return NewRelatedParty for default RelatedParty initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRelatedPartyFormGroup(sampleWithNewData);

        const relatedParty = service.getRelatedParty(formGroup) as any;

        expect(relatedParty).toMatchObject(sampleWithNewData);
      });

      it('should return NewRelatedParty for empty RelatedParty initial value', () => {
        const formGroup = service.createRelatedPartyFormGroup();

        const relatedParty = service.getRelatedParty(formGroup) as any;

        expect(relatedParty).toMatchObject({});
      });

      it('should return IRelatedParty', () => {
        const formGroup = service.createRelatedPartyFormGroup(sampleWithRequiredData);

        const relatedParty = service.getRelatedParty(formGroup) as any;

        expect(relatedParty).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRelatedParty should not enable id FormControl', () => {
        const formGroup = service.createRelatedPartyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRelatedParty should disable id FormControl', () => {
        const formGroup = service.createRelatedPartyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
