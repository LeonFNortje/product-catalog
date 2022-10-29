import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../characteristic-value-specification.test-samples';

import { CharacteristicValueSpecificationFormService } from './characteristic-value-specification-form.service';

describe('CharacteristicValueSpecification Form Service', () => {
  let service: CharacteristicValueSpecificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CharacteristicValueSpecificationFormService);
  });

  describe('Service methods', () => {
    describe('createCharacteristicValueSpecificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isDefault: expect.any(Object),
            rangeInterval: expect.any(Object),
            regex: expect.any(Object),
            unitOfMeasure: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            productSpecificationCharacteristicRelationship: expect.any(Object),
          })
        );
      });

      it('passing ICharacteristicValueSpecification should create a new form with FormGroup', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isDefault: expect.any(Object),
            rangeInterval: expect.any(Object),
            regex: expect.any(Object),
            unitOfMeasure: expect.any(Object),
            validForFrom: expect.any(Object),
            validForTo: expect.any(Object),
            valueType: expect.any(Object),
            schemaLocation: expect.any(Object),
            type: expect.any(Object),
            productSpecificationCharacteristicRelationship: expect.any(Object),
          })
        );
      });
    });

    describe('getCharacteristicValueSpecification', () => {
      it('should return NewCharacteristicValueSpecification for default CharacteristicValueSpecification initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCharacteristicValueSpecificationFormGroup(sampleWithNewData);

        const characteristicValueSpecification = service.getCharacteristicValueSpecification(formGroup) as any;

        expect(characteristicValueSpecification).toMatchObject(sampleWithNewData);
      });

      it('should return NewCharacteristicValueSpecification for empty CharacteristicValueSpecification initial value', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup();

        const characteristicValueSpecification = service.getCharacteristicValueSpecification(formGroup) as any;

        expect(characteristicValueSpecification).toMatchObject({});
      });

      it('should return ICharacteristicValueSpecification', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup(sampleWithRequiredData);

        const characteristicValueSpecification = service.getCharacteristicValueSpecification(formGroup) as any;

        expect(characteristicValueSpecification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICharacteristicValueSpecification should not enable id FormControl', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCharacteristicValueSpecification should disable id FormControl', () => {
        const formGroup = service.createCharacteristicValueSpecificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
