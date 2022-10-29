import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import {
  IProductSpecificationCharacteristicRelationship,
  NewProductSpecificationCharacteristicRelationship,
} from '../product-specification-characteristic-relationship.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSpecificationCharacteristicRelationship for edit and NewProductSpecificationCharacteristicRelationshipFormGroupInput for create.
 */
type ProductSpecificationCharacteristicRelationshipFormGroupInput =
  | IProductSpecificationCharacteristicRelationship
  | PartialWithRequiredKeyOf<NewProductSpecificationCharacteristicRelationship>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductSpecificationCharacteristicRelationship | NewProductSpecificationCharacteristicRelationship> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

type ProductSpecificationCharacteristicRelationshipFormRawValue = FormValueOf<IProductSpecificationCharacteristicRelationship>;

type NewProductSpecificationCharacteristicRelationshipFormRawValue = FormValueOf<NewProductSpecificationCharacteristicRelationship>;

type ProductSpecificationCharacteristicRelationshipFormDefaults = Pick<
  NewProductSpecificationCharacteristicRelationship,
  'id' | 'validForFrom' | 'validForTo'
>;

type ProductSpecificationCharacteristicRelationshipFormGroupContent = {
  href: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['href']>;
  id: FormControl<
    ProductSpecificationCharacteristicRelationshipFormRawValue['id'] | NewProductSpecificationCharacteristicRelationship['id']
  >;
  name: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['name']>;
  relationshipType: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['relationshipType']>;
  validForFrom: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['validForFrom']>;
  validForTo: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['validForTo']>;
  schemaLocation: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['schemaLocation']>;
  type: FormControl<ProductSpecificationCharacteristicRelationshipFormRawValue['type']>;
};

export type ProductSpecificationCharacteristicRelationshipFormGroup =
  FormGroup<ProductSpecificationCharacteristicRelationshipFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicRelationshipFormService {
  createProductSpecificationCharacteristicRelationshipFormGroup(
    productSpecificationCharacteristicRelationship: ProductSpecificationCharacteristicRelationshipFormGroupInput = { id: null }
  ): ProductSpecificationCharacteristicRelationshipFormGroup {
    const productSpecificationCharacteristicRelationshipRawValue =
      this.convertProductSpecificationCharacteristicRelationshipToProductSpecificationCharacteristicRelationshipRawValue({
        ...this.getFormDefaults(),
        ...productSpecificationCharacteristicRelationship,
      });
    return new FormGroup<ProductSpecificationCharacteristicRelationshipFormGroupContent>({
      href: new FormControl(productSpecificationCharacteristicRelationshipRawValue.href),
      id: new FormControl(
        {
          value: productSpecificationCharacteristicRelationshipRawValue.id,
          disabled: productSpecificationCharacteristicRelationshipRawValue.id !== null,
        },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(productSpecificationCharacteristicRelationshipRawValue.name),
      relationshipType: new FormControl(productSpecificationCharacteristicRelationshipRawValue.relationshipType),
      validForFrom: new FormControl(productSpecificationCharacteristicRelationshipRawValue.validForFrom),
      validForTo: new FormControl(productSpecificationCharacteristicRelationshipRawValue.validForTo),
      schemaLocation: new FormControl(productSpecificationCharacteristicRelationshipRawValue.schemaLocation),
      type: new FormControl(productSpecificationCharacteristicRelationshipRawValue.type),
    });
  }

  getProductSpecificationCharacteristicRelationship(
    form: ProductSpecificationCharacteristicRelationshipFormGroup
  ): IProductSpecificationCharacteristicRelationship | NewProductSpecificationCharacteristicRelationship {
    return this.convertProductSpecificationCharacteristicRelationshipRawValueToProductSpecificationCharacteristicRelationship(
      form.getRawValue() as
        | ProductSpecificationCharacteristicRelationshipFormRawValue
        | NewProductSpecificationCharacteristicRelationshipFormRawValue
    );
  }

  resetForm(
    form: ProductSpecificationCharacteristicRelationshipFormGroup,
    productSpecificationCharacteristicRelationship: ProductSpecificationCharacteristicRelationshipFormGroupInput
  ): void {
    const productSpecificationCharacteristicRelationshipRawValue =
      this.convertProductSpecificationCharacteristicRelationshipToProductSpecificationCharacteristicRelationshipRawValue({
        ...this.getFormDefaults(),
        ...productSpecificationCharacteristicRelationship,
      });
    form.reset(
      {
        ...productSpecificationCharacteristicRelationshipRawValue,
        id: {
          value: productSpecificationCharacteristicRelationshipRawValue.id,
          disabled: productSpecificationCharacteristicRelationshipRawValue.id !== null,
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSpecificationCharacteristicRelationshipFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertProductSpecificationCharacteristicRelationshipRawValueToProductSpecificationCharacteristicRelationship(
    rawProductSpecificationCharacteristicRelationship:
      | ProductSpecificationCharacteristicRelationshipFormRawValue
      | NewProductSpecificationCharacteristicRelationshipFormRawValue
  ): IProductSpecificationCharacteristicRelationship | NewProductSpecificationCharacteristicRelationship {
    return {
      ...rawProductSpecificationCharacteristicRelationship,
      validForFrom: dayjs(rawProductSpecificationCharacteristicRelationship.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawProductSpecificationCharacteristicRelationship.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertProductSpecificationCharacteristicRelationshipToProductSpecificationCharacteristicRelationshipRawValue(
    productSpecificationCharacteristicRelationship:
      | IProductSpecificationCharacteristicRelationship
      | (Partial<NewProductSpecificationCharacteristicRelationship> & ProductSpecificationCharacteristicRelationshipFormDefaults)
  ):
    | ProductSpecificationCharacteristicRelationshipFormRawValue
    | PartialWithRequiredKeyOf<NewProductSpecificationCharacteristicRelationshipFormRawValue> {
    return {
      ...productSpecificationCharacteristicRelationship,
      validForFrom: productSpecificationCharacteristicRelationship.validForFrom
        ? productSpecificationCharacteristicRelationship.validForFrom.format(DATE_TIME_FORMAT)
        : undefined,
      validForTo: productSpecificationCharacteristicRelationship.validForTo
        ? productSpecificationCharacteristicRelationship.validForTo.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
