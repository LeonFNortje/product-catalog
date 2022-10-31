import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../related-place.test-samples';

import { RelatedPlaceFormService } from './related-place-form.service';

describe('RelatedPlace Form Service', () => {
  let service: RelatedPlaceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RelatedPlaceFormService);
  });

  describe('Service methods', () => {
    describe('createRelatedPlaceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRelatedPlaceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            href: expect.any(Object),
            name: expect.any(Object),
            role: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IRelatedPlace should create a new form with FormGroup', () => {
        const formGroup = service.createRelatedPlaceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            href: expect.any(Object),
            name: expect.any(Object),
            role: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getRelatedPlace', () => {
      it('should return NewRelatedPlace for default RelatedPlace initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRelatedPlaceFormGroup(sampleWithNewData);

        const relatedPlace = service.getRelatedPlace(formGroup) as any;

        expect(relatedPlace).toMatchObject(sampleWithNewData);
      });

      it('should return NewRelatedPlace for empty RelatedPlace initial value', () => {
        const formGroup = service.createRelatedPlaceFormGroup();

        const relatedPlace = service.getRelatedPlace(formGroup) as any;

        expect(relatedPlace).toMatchObject({});
      });

      it('should return IRelatedPlace', () => {
        const formGroup = service.createRelatedPlaceFormGroup(sampleWithRequiredData);

        const relatedPlace = service.getRelatedPlace(formGroup) as any;

        expect(relatedPlace).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRelatedPlace should not enable id FormControl', () => {
        const formGroup = service.createRelatedPlaceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRelatedPlace should disable id FormControl', () => {
        const formGroup = service.createRelatedPlaceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
