import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductSpecification, NewProductSpecification } from '../product-specification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSpecification for edit and NewProductSpecificationFormGroupInput for create.
 */
type ProductSpecificationFormGroupInput = IProductSpecification | PartialWithRequiredKeyOf<NewProductSpecification>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductSpecification | NewProductSpecification> = Omit<T, 'lastUpdate' | 'validForFrom' | 'validForTo'> & {
  lastUpdate?: string | null;
  validForFrom?: string | null;
  validForTo?: string | null;
};

type ProductSpecificationFormRawValue = FormValueOf<IProductSpecification>;

type NewProductSpecificationFormRawValue = FormValueOf<NewProductSpecification>;

type ProductSpecificationFormDefaults = Pick<NewProductSpecification, 'id' | 'isBundle' | 'lastUpdate' | 'validForFrom' | 'validForTo'>;

type ProductSpecificationFormGroupContent = {
  brand: FormControl<ProductSpecificationFormRawValue['brand']>;
  description: FormControl<ProductSpecificationFormRawValue['description']>;
  href: FormControl<ProductSpecificationFormRawValue['href']>;
  id: FormControl<ProductSpecificationFormRawValue['id'] | NewProductSpecification['id']>;
  name: FormControl<ProductSpecificationFormRawValue['name']>;
  isBundle: FormControl<ProductSpecificationFormRawValue['isBundle']>;
  lastUpdate: FormControl<ProductSpecificationFormRawValue['lastUpdate']>;
  lifecycleStatus: FormControl<ProductSpecificationFormRawValue['lifecycleStatus']>;
  productNumber: FormControl<ProductSpecificationFormRawValue['productNumber']>;
  validForFrom: FormControl<ProductSpecificationFormRawValue['validForFrom']>;
  validForTo: FormControl<ProductSpecificationFormRawValue['validForTo']>;
  version: FormControl<ProductSpecificationFormRawValue['version']>;
  schemaLocation: FormControl<ProductSpecificationFormRawValue['schemaLocation']>;
  type: FormControl<ProductSpecificationFormRawValue['type']>;
  targetProductSchema: FormControl<ProductSpecificationFormRawValue['targetProductSchema']>;
  resourceSpecificationRef: FormControl<ProductSpecificationFormRawValue['resourceSpecificationRef']>;
  attachmentRefOrValue: FormControl<ProductSpecificationFormRawValue['attachmentRefOrValue']>;
  relatedParty: FormControl<ProductSpecificationFormRawValue['relatedParty']>;
  serviceSpecificationRef: FormControl<ProductSpecificationFormRawValue['serviceSpecificationRef']>;
  productSpecificationRelationship: FormControl<ProductSpecificationFormRawValue['productSpecificationRelationship']>;
  bundledProductSpecification: FormControl<ProductSpecificationFormRawValue['bundledProductSpecification']>;
  productSpecificationCharacteristic: FormControl<ProductSpecificationFormRawValue['productSpecificationCharacteristic']>;
};

export type ProductSpecificationFormGroup = FormGroup<ProductSpecificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSpecificationFormService {
  createProductSpecificationFormGroup(
    productSpecification: ProductSpecificationFormGroupInput = { id: null }
  ): ProductSpecificationFormGroup {
    const productSpecificationRawValue = this.convertProductSpecificationToProductSpecificationRawValue({
      ...this.getFormDefaults(),
      ...productSpecification,
    });
    return new FormGroup<ProductSpecificationFormGroupContent>({
      brand: new FormControl(productSpecificationRawValue.brand),
      description: new FormControl(productSpecificationRawValue.description),
      href: new FormControl(productSpecificationRawValue.href),
      id: new FormControl(
        { value: productSpecificationRawValue.id, disabled: productSpecificationRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(productSpecificationRawValue.name),
      isBundle: new FormControl(productSpecificationRawValue.isBundle),
      lastUpdate: new FormControl(productSpecificationRawValue.lastUpdate),
      lifecycleStatus: new FormControl(productSpecificationRawValue.lifecycleStatus),
      productNumber: new FormControl(productSpecificationRawValue.productNumber),
      validForFrom: new FormControl(productSpecificationRawValue.validForFrom),
      validForTo: new FormControl(productSpecificationRawValue.validForTo),
      version: new FormControl(productSpecificationRawValue.version),
      schemaLocation: new FormControl(productSpecificationRawValue.schemaLocation),
      type: new FormControl(productSpecificationRawValue.type),
      targetProductSchema: new FormControl(productSpecificationRawValue.targetProductSchema),
      resourceSpecificationRef: new FormControl(productSpecificationRawValue.resourceSpecificationRef),
      attachmentRefOrValue: new FormControl(productSpecificationRawValue.attachmentRefOrValue),
      relatedParty: new FormControl(productSpecificationRawValue.relatedParty),
      serviceSpecificationRef: new FormControl(productSpecificationRawValue.serviceSpecificationRef),
      productSpecificationRelationship: new FormControl(productSpecificationRawValue.productSpecificationRelationship),
      bundledProductSpecification: new FormControl(productSpecificationRawValue.bundledProductSpecification),
      productSpecificationCharacteristic: new FormControl(productSpecificationRawValue.productSpecificationCharacteristic),
    });
  }

  getProductSpecification(form: ProductSpecificationFormGroup): IProductSpecification | NewProductSpecification {
    return this.convertProductSpecificationRawValueToProductSpecification(
      form.getRawValue() as ProductSpecificationFormRawValue | NewProductSpecificationFormRawValue
    );
  }

  resetForm(form: ProductSpecificationFormGroup, productSpecification: ProductSpecificationFormGroupInput): void {
    const productSpecificationRawValue = this.convertProductSpecificationToProductSpecificationRawValue({
      ...this.getFormDefaults(),
      ...productSpecification,
    });
    form.reset(
      {
        ...productSpecificationRawValue,
        id: { value: productSpecificationRawValue.id, disabled: productSpecificationRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSpecificationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isBundle: false,
      lastUpdate: currentTime,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertProductSpecificationRawValueToProductSpecification(
    rawProductSpecification: ProductSpecificationFormRawValue | NewProductSpecificationFormRawValue
  ): IProductSpecification | NewProductSpecification {
    return {
      ...rawProductSpecification,
      lastUpdate: dayjs(rawProductSpecification.lastUpdate, DATE_TIME_FORMAT),
      validForFrom: dayjs(rawProductSpecification.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawProductSpecification.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertProductSpecificationToProductSpecificationRawValue(
    productSpecification: IProductSpecification | (Partial<NewProductSpecification> & ProductSpecificationFormDefaults)
  ): ProductSpecificationFormRawValue | PartialWithRequiredKeyOf<NewProductSpecificationFormRawValue> {
    return {
      ...productSpecification,
      lastUpdate: productSpecification.lastUpdate ? productSpecification.lastUpdate.format(DATE_TIME_FORMAT) : undefined,
      validForFrom: productSpecification.validForFrom ? productSpecification.validForFrom.format(DATE_TIME_FORMAT) : undefined,
      validForTo: productSpecification.validForTo ? productSpecification.validForTo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
