import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentMethod for edit and NewPaymentMethodFormGroupInput for create.
 */
type PaymentMethodFormGroupInput = IPaymentMethod | PartialWithRequiredKeyOf<NewPaymentMethod>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPaymentMethod | NewPaymentMethod> = Omit<T, 'statusDate' | 'validForFrom' | 'validForTo'> & {
  statusDate?: string | null;
  validForFrom?: string | null;
  validForTo?: string | null;
};

type PaymentMethodFormRawValue = FormValueOf<IPaymentMethod>;

type NewPaymentMethodFormRawValue = FormValueOf<NewPaymentMethod>;

type PaymentMethodFormDefaults = Pick<NewPaymentMethod, 'id' | 'isPreferred' | 'statusDate' | 'validForFrom' | 'validForTo'>;

type PaymentMethodFormGroupContent = {
  id: FormControl<PaymentMethodFormRawValue['id'] | NewPaymentMethod['id']>;
  href: FormControl<PaymentMethodFormRawValue['href']>;
  authorizationCode: FormControl<PaymentMethodFormRawValue['authorizationCode']>;
  description: FormControl<PaymentMethodFormRawValue['description']>;
  isPreferred: FormControl<PaymentMethodFormRawValue['isPreferred']>;
  name: FormControl<PaymentMethodFormRawValue['name']>;
  status: FormControl<PaymentMethodFormRawValue['status']>;
  statusDate: FormControl<PaymentMethodFormRawValue['statusDate']>;
  statusReason: FormControl<PaymentMethodFormRawValue['statusReason']>;
  validForFrom: FormControl<PaymentMethodFormRawValue['validForFrom']>;
  validForTo: FormControl<PaymentMethodFormRawValue['validForTo']>;
  schemaLocation: FormControl<PaymentMethodFormRawValue['schemaLocation']>;
  type: FormControl<PaymentMethodFormRawValue['type']>;
  relatedPlace: FormControl<PaymentMethodFormRawValue['relatedPlace']>;
  relatedParty: FormControl<PaymentMethodFormRawValue['relatedParty']>;
};

export type PaymentMethodFormGroup = FormGroup<PaymentMethodFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodFormService {
  createPaymentMethodFormGroup(paymentMethod: PaymentMethodFormGroupInput = { id: null }): PaymentMethodFormGroup {
    const paymentMethodRawValue = this.convertPaymentMethodToPaymentMethodRawValue({
      ...this.getFormDefaults(),
      ...paymentMethod,
    });
    return new FormGroup<PaymentMethodFormGroupContent>({
      id: new FormControl(
        { value: paymentMethodRawValue.id, disabled: paymentMethodRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      href: new FormControl(paymentMethodRawValue.href),
      authorizationCode: new FormControl(paymentMethodRawValue.authorizationCode),
      description: new FormControl(paymentMethodRawValue.description),
      isPreferred: new FormControl(paymentMethodRawValue.isPreferred),
      name: new FormControl(paymentMethodRawValue.name),
      status: new FormControl(paymentMethodRawValue.status),
      statusDate: new FormControl(paymentMethodRawValue.statusDate),
      statusReason: new FormControl(paymentMethodRawValue.statusReason),
      validForFrom: new FormControl(paymentMethodRawValue.validForFrom),
      validForTo: new FormControl(paymentMethodRawValue.validForTo),
      schemaLocation: new FormControl(paymentMethodRawValue.schemaLocation),
      type: new FormControl(paymentMethodRawValue.type),
      relatedPlace: new FormControl(paymentMethodRawValue.relatedPlace),
      relatedParty: new FormControl(paymentMethodRawValue.relatedParty),
    });
  }

  getPaymentMethod(form: PaymentMethodFormGroup): IPaymentMethod | NewPaymentMethod {
    return this.convertPaymentMethodRawValueToPaymentMethod(form.getRawValue() as PaymentMethodFormRawValue | NewPaymentMethodFormRawValue);
  }

  resetForm(form: PaymentMethodFormGroup, paymentMethod: PaymentMethodFormGroupInput): void {
    const paymentMethodRawValue = this.convertPaymentMethodToPaymentMethodRawValue({ ...this.getFormDefaults(), ...paymentMethod });
    form.reset(
      {
        ...paymentMethodRawValue,
        id: { value: paymentMethodRawValue.id, disabled: paymentMethodRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentMethodFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isPreferred: false,
      statusDate: currentTime,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertPaymentMethodRawValueToPaymentMethod(
    rawPaymentMethod: PaymentMethodFormRawValue | NewPaymentMethodFormRawValue
  ): IPaymentMethod | NewPaymentMethod {
    return {
      ...rawPaymentMethod,
      statusDate: dayjs(rawPaymentMethod.statusDate, DATE_TIME_FORMAT),
      validForFrom: dayjs(rawPaymentMethod.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawPaymentMethod.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertPaymentMethodToPaymentMethodRawValue(
    paymentMethod: IPaymentMethod | (Partial<NewPaymentMethod> & PaymentMethodFormDefaults)
  ): PaymentMethodFormRawValue | PartialWithRequiredKeyOf<NewPaymentMethodFormRawValue> {
    return {
      ...paymentMethod,
      statusDate: paymentMethod.statusDate ? paymentMethod.statusDate.format(DATE_TIME_FORMAT) : undefined,
      validForFrom: paymentMethod.validForFrom ? paymentMethod.validForFrom.format(DATE_TIME_FORMAT) : undefined,
      validForTo: paymentMethod.validForTo ? paymentMethod.validForTo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
