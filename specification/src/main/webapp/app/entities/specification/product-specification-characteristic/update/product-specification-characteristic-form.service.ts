import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductSpecificationCharacteristic, NewProductSpecificationCharacteristic } from '../product-specification-characteristic.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSpecificationCharacteristic for edit and NewProductSpecificationCharacteristicFormGroupInput for create.
 */
type ProductSpecificationCharacteristicFormGroupInput =
  | IProductSpecificationCharacteristic
  | PartialWithRequiredKeyOf<NewProductSpecificationCharacteristic>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductSpecificationCharacteristic | NewProductSpecificationCharacteristic> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

type ProductSpecificationCharacteristicFormRawValue = FormValueOf<IProductSpecificationCharacteristic>;

type NewProductSpecificationCharacteristicFormRawValue = FormValueOf<NewProductSpecificationCharacteristic>;

type ProductSpecificationCharacteristicFormDefaults = Pick<
  NewProductSpecificationCharacteristic,
  'configurable' | 'extensible' | 'id' | 'isUnique' | 'validForFrom' | 'validForTo'
>;

type ProductSpecificationCharacteristicFormGroupContent = {
  configurable: FormControl<ProductSpecificationCharacteristicFormRawValue['configurable']>;
  description: FormControl<ProductSpecificationCharacteristicFormRawValue['description']>;
  extensible: FormControl<ProductSpecificationCharacteristicFormRawValue['extensible']>;
  id: FormControl<ProductSpecificationCharacteristicFormRawValue['id'] | NewProductSpecificationCharacteristic['id']>;
  isUnique: FormControl<ProductSpecificationCharacteristicFormRawValue['isUnique']>;
  maxCardinality: FormControl<ProductSpecificationCharacteristicFormRawValue['maxCardinality']>;
  minCardinality: FormControl<ProductSpecificationCharacteristicFormRawValue['minCardinality']>;
  name: FormControl<ProductSpecificationCharacteristicFormRawValue['name']>;
  regex: FormControl<ProductSpecificationCharacteristicFormRawValue['regex']>;
  validForFrom: FormControl<ProductSpecificationCharacteristicFormRawValue['validForFrom']>;
  validForTo: FormControl<ProductSpecificationCharacteristicFormRawValue['validForTo']>;
  valueType: FormControl<ProductSpecificationCharacteristicFormRawValue['valueType']>;
  schemaLocation: FormControl<ProductSpecificationCharacteristicFormRawValue['schemaLocation']>;
  type: FormControl<ProductSpecificationCharacteristicFormRawValue['type']>;
  valueSchemaLocation: FormControl<ProductSpecificationCharacteristicFormRawValue['valueSchemaLocation']>;
  productSpecificationCharacteristicRelationship: FormControl<
    ProductSpecificationCharacteristicFormRawValue['productSpecificationCharacteristicRelationship']
  >;
};

export type ProductSpecificationCharacteristicFormGroup = FormGroup<ProductSpecificationCharacteristicFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationCharacteristicFormService {
  createProductSpecificationCharacteristicFormGroup(
    productSpecificationCharacteristic: ProductSpecificationCharacteristicFormGroupInput = { id: null }
  ): ProductSpecificationCharacteristicFormGroup {
    const productSpecificationCharacteristicRawValue =
      this.convertProductSpecificationCharacteristicToProductSpecificationCharacteristicRawValue({
        ...this.getFormDefaults(),
        ...productSpecificationCharacteristic,
      });
    return new FormGroup<ProductSpecificationCharacteristicFormGroupContent>({
      configurable: new FormControl(productSpecificationCharacteristicRawValue.configurable),
      description: new FormControl(productSpecificationCharacteristicRawValue.description),
      extensible: new FormControl(productSpecificationCharacteristicRawValue.extensible),
      id: new FormControl(
        { value: productSpecificationCharacteristicRawValue.id, disabled: productSpecificationCharacteristicRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isUnique: new FormControl(productSpecificationCharacteristicRawValue.isUnique),
      maxCardinality: new FormControl(productSpecificationCharacteristicRawValue.maxCardinality),
      minCardinality: new FormControl(productSpecificationCharacteristicRawValue.minCardinality),
      name: new FormControl(productSpecificationCharacteristicRawValue.name),
      regex: new FormControl(productSpecificationCharacteristicRawValue.regex),
      validForFrom: new FormControl(productSpecificationCharacteristicRawValue.validForFrom),
      validForTo: new FormControl(productSpecificationCharacteristicRawValue.validForTo),
      valueType: new FormControl(productSpecificationCharacteristicRawValue.valueType),
      schemaLocation: new FormControl(productSpecificationCharacteristicRawValue.schemaLocation),
      type: new FormControl(productSpecificationCharacteristicRawValue.type),
      valueSchemaLocation: new FormControl(productSpecificationCharacteristicRawValue.valueSchemaLocation),
      productSpecificationCharacteristicRelationship: new FormControl(
        productSpecificationCharacteristicRawValue.productSpecificationCharacteristicRelationship
      ),
    });
  }

  getProductSpecificationCharacteristic(
    form: ProductSpecificationCharacteristicFormGroup
  ): IProductSpecificationCharacteristic | NewProductSpecificationCharacteristic {
    return this.convertProductSpecificationCharacteristicRawValueToProductSpecificationCharacteristic(
      form.getRawValue() as ProductSpecificationCharacteristicFormRawValue | NewProductSpecificationCharacteristicFormRawValue
    );
  }

  resetForm(
    form: ProductSpecificationCharacteristicFormGroup,
    productSpecificationCharacteristic: ProductSpecificationCharacteristicFormGroupInput
  ): void {
    const productSpecificationCharacteristicRawValue =
      this.convertProductSpecificationCharacteristicToProductSpecificationCharacteristicRawValue({
        ...this.getFormDefaults(),
        ...productSpecificationCharacteristic,
      });
    form.reset(
      {
        ...productSpecificationCharacteristicRawValue,
        id: { value: productSpecificationCharacteristicRawValue.id, disabled: productSpecificationCharacteristicRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSpecificationCharacteristicFormDefaults {
    const currentTime = dayjs();

    return {
      configurable: false,
      extensible: false,
      id: null,
      isUnique: false,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertProductSpecificationCharacteristicRawValueToProductSpecificationCharacteristic(
    rawProductSpecificationCharacteristic:
      | ProductSpecificationCharacteristicFormRawValue
      | NewProductSpecificationCharacteristicFormRawValue
  ): IProductSpecificationCharacteristic | NewProductSpecificationCharacteristic {
    return {
      ...rawProductSpecificationCharacteristic,
      validForFrom: dayjs(rawProductSpecificationCharacteristic.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawProductSpecificationCharacteristic.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertProductSpecificationCharacteristicToProductSpecificationCharacteristicRawValue(
    productSpecificationCharacteristic:
      | IProductSpecificationCharacteristic
      | (Partial<NewProductSpecificationCharacteristic> & ProductSpecificationCharacteristicFormDefaults)
  ): ProductSpecificationCharacteristicFormRawValue | PartialWithRequiredKeyOf<NewProductSpecificationCharacteristicFormRawValue> {
    return {
      ...productSpecificationCharacteristic,
      validForFrom: productSpecificationCharacteristic.validForFrom
        ? productSpecificationCharacteristic.validForFrom.format(DATE_TIME_FORMAT)
        : undefined,
      validForTo: productSpecificationCharacteristic.validForTo
        ? productSpecificationCharacteristic.validForTo.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
