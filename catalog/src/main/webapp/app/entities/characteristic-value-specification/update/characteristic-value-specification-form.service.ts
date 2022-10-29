import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICharacteristicValueSpecification, NewCharacteristicValueSpecification } from '../characteristic-value-specification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICharacteristicValueSpecification for edit and NewCharacteristicValueSpecificationFormGroupInput for create.
 */
type CharacteristicValueSpecificationFormGroupInput =
  | ICharacteristicValueSpecification
  | PartialWithRequiredKeyOf<NewCharacteristicValueSpecification>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICharacteristicValueSpecification | NewCharacteristicValueSpecification> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

type CharacteristicValueSpecificationFormRawValue = FormValueOf<ICharacteristicValueSpecification>;

type NewCharacteristicValueSpecificationFormRawValue = FormValueOf<NewCharacteristicValueSpecification>;

type CharacteristicValueSpecificationFormDefaults = Pick<
  NewCharacteristicValueSpecification,
  'id' | 'isDefault' | 'validForFrom' | 'validForTo'
>;

type CharacteristicValueSpecificationFormGroupContent = {
  id: FormControl<CharacteristicValueSpecificationFormRawValue['id'] | NewCharacteristicValueSpecification['id']>;
  isDefault: FormControl<CharacteristicValueSpecificationFormRawValue['isDefault']>;
  rangeInterval: FormControl<CharacteristicValueSpecificationFormRawValue['rangeInterval']>;
  regex: FormControl<CharacteristicValueSpecificationFormRawValue['regex']>;
  unitOfMeasure: FormControl<CharacteristicValueSpecificationFormRawValue['unitOfMeasure']>;
  validForFrom: FormControl<CharacteristicValueSpecificationFormRawValue['validForFrom']>;
  validForTo: FormControl<CharacteristicValueSpecificationFormRawValue['validForTo']>;
  valueType: FormControl<CharacteristicValueSpecificationFormRawValue['valueType']>;
  schemaLocation: FormControl<CharacteristicValueSpecificationFormRawValue['schemaLocation']>;
  type: FormControl<CharacteristicValueSpecificationFormRawValue['type']>;
  productSpecificationCharacteristicRelationship: FormControl<
    CharacteristicValueSpecificationFormRawValue['productSpecificationCharacteristicRelationship']
  >;
};

export type CharacteristicValueSpecificationFormGroup = FormGroup<CharacteristicValueSpecificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CharacteristicValueSpecificationFormService {
  createCharacteristicValueSpecificationFormGroup(
    characteristicValueSpecification: CharacteristicValueSpecificationFormGroupInput = { id: null }
  ): CharacteristicValueSpecificationFormGroup {
    const characteristicValueSpecificationRawValue = this.convertCharacteristicValueSpecificationToCharacteristicValueSpecificationRawValue(
      {
        ...this.getFormDefaults(),
        ...characteristicValueSpecification,
      }
    );
    return new FormGroup<CharacteristicValueSpecificationFormGroupContent>({
      id: new FormControl(
        { value: characteristicValueSpecificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isDefault: new FormControl(characteristicValueSpecificationRawValue.isDefault),
      rangeInterval: new FormControl(characteristicValueSpecificationRawValue.rangeInterval),
      regex: new FormControl(characteristicValueSpecificationRawValue.regex),
      unitOfMeasure: new FormControl(characteristicValueSpecificationRawValue.unitOfMeasure),
      validForFrom: new FormControl(characteristicValueSpecificationRawValue.validForFrom),
      validForTo: new FormControl(characteristicValueSpecificationRawValue.validForTo),
      valueType: new FormControl(characteristicValueSpecificationRawValue.valueType),
      schemaLocation: new FormControl(characteristicValueSpecificationRawValue.schemaLocation),
      type: new FormControl(characteristicValueSpecificationRawValue.type),
      productSpecificationCharacteristicRelationship: new FormControl(
        characteristicValueSpecificationRawValue.productSpecificationCharacteristicRelationship
      ),
    });
  }

  getCharacteristicValueSpecification(
    form: CharacteristicValueSpecificationFormGroup
  ): ICharacteristicValueSpecification | NewCharacteristicValueSpecification {
    return this.convertCharacteristicValueSpecificationRawValueToCharacteristicValueSpecification(
      form.getRawValue() as CharacteristicValueSpecificationFormRawValue | NewCharacteristicValueSpecificationFormRawValue
    );
  }

  resetForm(
    form: CharacteristicValueSpecificationFormGroup,
    characteristicValueSpecification: CharacteristicValueSpecificationFormGroupInput
  ): void {
    const characteristicValueSpecificationRawValue = this.convertCharacteristicValueSpecificationToCharacteristicValueSpecificationRawValue(
      { ...this.getFormDefaults(), ...characteristicValueSpecification }
    );
    form.reset(
      {
        ...characteristicValueSpecificationRawValue,
        id: { value: characteristicValueSpecificationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CharacteristicValueSpecificationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDefault: false,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertCharacteristicValueSpecificationRawValueToCharacteristicValueSpecification(
    rawCharacteristicValueSpecification: CharacteristicValueSpecificationFormRawValue | NewCharacteristicValueSpecificationFormRawValue
  ): ICharacteristicValueSpecification | NewCharacteristicValueSpecification {
    return {
      ...rawCharacteristicValueSpecification,
      validForFrom: dayjs(rawCharacteristicValueSpecification.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawCharacteristicValueSpecification.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertCharacteristicValueSpecificationToCharacteristicValueSpecificationRawValue(
    characteristicValueSpecification:
      | ICharacteristicValueSpecification
      | (Partial<NewCharacteristicValueSpecification> & CharacteristicValueSpecificationFormDefaults)
  ): CharacteristicValueSpecificationFormRawValue | PartialWithRequiredKeyOf<NewCharacteristicValueSpecificationFormRawValue> {
    return {
      ...characteristicValueSpecification,
      validForFrom: characteristicValueSpecification.validForFrom
        ? characteristicValueSpecification.validForFrom.format(DATE_TIME_FORMAT)
        : undefined,
      validForTo: characteristicValueSpecification.validForTo
        ? characteristicValueSpecification.validForTo.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
