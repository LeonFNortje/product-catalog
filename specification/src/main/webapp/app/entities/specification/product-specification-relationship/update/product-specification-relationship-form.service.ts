import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductSpecificationRelationship, NewProductSpecificationRelationship } from '../product-specification-relationship.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSpecificationRelationship for edit and NewProductSpecificationRelationshipFormGroupInput for create.
 */
type ProductSpecificationRelationshipFormGroupInput =
  | IProductSpecificationRelationship
  | PartialWithRequiredKeyOf<NewProductSpecificationRelationship>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductSpecificationRelationship | NewProductSpecificationRelationship> = Omit<
  T,
  'validForFrom' | 'validForTo'
> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

type ProductSpecificationRelationshipFormRawValue = FormValueOf<IProductSpecificationRelationship>;

type NewProductSpecificationRelationshipFormRawValue = FormValueOf<NewProductSpecificationRelationship>;

type ProductSpecificationRelationshipFormDefaults = Pick<NewProductSpecificationRelationship, 'id' | 'validForFrom' | 'validForTo'>;

type ProductSpecificationRelationshipFormGroupContent = {
  href: FormControl<ProductSpecificationRelationshipFormRawValue['href']>;
  id: FormControl<ProductSpecificationRelationshipFormRawValue['id'] | NewProductSpecificationRelationship['id']>;
  name: FormControl<ProductSpecificationRelationshipFormRawValue['name']>;
  relationshipType: FormControl<ProductSpecificationRelationshipFormRawValue['relationshipType']>;
  validForFrom: FormControl<ProductSpecificationRelationshipFormRawValue['validForFrom']>;
  validForTo: FormControl<ProductSpecificationRelationshipFormRawValue['validForTo']>;
  schemaLocation: FormControl<ProductSpecificationRelationshipFormRawValue['schemaLocation']>;
  type: FormControl<ProductSpecificationRelationshipFormRawValue['type']>;
};

export type ProductSpecificationRelationshipFormGroup = FormGroup<ProductSpecificationRelationshipFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationRelationshipFormService {
  createProductSpecificationRelationshipFormGroup(
    productSpecificationRelationship: ProductSpecificationRelationshipFormGroupInput = { id: null }
  ): ProductSpecificationRelationshipFormGroup {
    const productSpecificationRelationshipRawValue = this.convertProductSpecificationRelationshipToProductSpecificationRelationshipRawValue(
      {
        ...this.getFormDefaults(),
        ...productSpecificationRelationship,
      }
    );
    return new FormGroup<ProductSpecificationRelationshipFormGroupContent>({
      href: new FormControl(productSpecificationRelationshipRawValue.href),
      id: new FormControl(
        { value: productSpecificationRelationshipRawValue.id, disabled: productSpecificationRelationshipRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(productSpecificationRelationshipRawValue.name),
      relationshipType: new FormControl(productSpecificationRelationshipRawValue.relationshipType),
      validForFrom: new FormControl(productSpecificationRelationshipRawValue.validForFrom),
      validForTo: new FormControl(productSpecificationRelationshipRawValue.validForTo),
      schemaLocation: new FormControl(productSpecificationRelationshipRawValue.schemaLocation),
      type: new FormControl(productSpecificationRelationshipRawValue.type),
    });
  }

  getProductSpecificationRelationship(
    form: ProductSpecificationRelationshipFormGroup
  ): IProductSpecificationRelationship | NewProductSpecificationRelationship {
    return this.convertProductSpecificationRelationshipRawValueToProductSpecificationRelationship(
      form.getRawValue() as ProductSpecificationRelationshipFormRawValue | NewProductSpecificationRelationshipFormRawValue
    );
  }

  resetForm(
    form: ProductSpecificationRelationshipFormGroup,
    productSpecificationRelationship: ProductSpecificationRelationshipFormGroupInput
  ): void {
    const productSpecificationRelationshipRawValue = this.convertProductSpecificationRelationshipToProductSpecificationRelationshipRawValue(
      { ...this.getFormDefaults(), ...productSpecificationRelationship }
    );
    form.reset(
      {
        ...productSpecificationRelationshipRawValue,
        id: { value: productSpecificationRelationshipRawValue.id, disabled: productSpecificationRelationshipRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSpecificationRelationshipFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertProductSpecificationRelationshipRawValueToProductSpecificationRelationship(
    rawProductSpecificationRelationship: ProductSpecificationRelationshipFormRawValue | NewProductSpecificationRelationshipFormRawValue
  ): IProductSpecificationRelationship | NewProductSpecificationRelationship {
    return {
      ...rawProductSpecificationRelationship,
      validForFrom: dayjs(rawProductSpecificationRelationship.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawProductSpecificationRelationship.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertProductSpecificationRelationshipToProductSpecificationRelationshipRawValue(
    productSpecificationRelationship:
      | IProductSpecificationRelationship
      | (Partial<NewProductSpecificationRelationship> & ProductSpecificationRelationshipFormDefaults)
  ): ProductSpecificationRelationshipFormRawValue | PartialWithRequiredKeyOf<NewProductSpecificationRelationshipFormRawValue> {
    return {
      ...productSpecificationRelationship,
      validForFrom: productSpecificationRelationship.validForFrom
        ? productSpecificationRelationship.validForFrom.format(DATE_TIME_FORMAT)
        : undefined,
      validForTo: productSpecificationRelationship.validForTo
        ? productSpecificationRelationship.validForTo.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
