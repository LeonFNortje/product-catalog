import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAttachmentRefOrValue, NewAttachmentRefOrValue } from '../attachment-ref-or-value.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAttachmentRefOrValue for edit and NewAttachmentRefOrValueFormGroupInput for create.
 */
type AttachmentRefOrValueFormGroupInput = IAttachmentRefOrValue | PartialWithRequiredKeyOf<NewAttachmentRefOrValue>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAttachmentRefOrValue | NewAttachmentRefOrValue> = Omit<T, 'validForFrom' | 'validForTo'> & {
  validForFrom?: string | null;
  validForTo?: string | null;
};

type AttachmentRefOrValueFormRawValue = FormValueOf<IAttachmentRefOrValue>;

type NewAttachmentRefOrValueFormRawValue = FormValueOf<NewAttachmentRefOrValue>;

type AttachmentRefOrValueFormDefaults = Pick<NewAttachmentRefOrValue, 'id' | 'validForFrom' | 'validForTo'>;

type AttachmentRefOrValueFormGroupContent = {
  attachmentType: FormControl<AttachmentRefOrValueFormRawValue['attachmentType']>;
  content: FormControl<AttachmentRefOrValueFormRawValue['content']>;
  description: FormControl<AttachmentRefOrValueFormRawValue['description']>;
  href: FormControl<AttachmentRefOrValueFormRawValue['href']>;
  id: FormControl<AttachmentRefOrValueFormRawValue['id'] | NewAttachmentRefOrValue['id']>;
  mimeType: FormControl<AttachmentRefOrValueFormRawValue['mimeType']>;
  name: FormControl<AttachmentRefOrValueFormRawValue['name']>;
  sizeOfBytes: FormControl<AttachmentRefOrValueFormRawValue['sizeOfBytes']>;
  url: FormControl<AttachmentRefOrValueFormRawValue['url']>;
  validForFrom: FormControl<AttachmentRefOrValueFormRawValue['validForFrom']>;
  validForTo: FormControl<AttachmentRefOrValueFormRawValue['validForTo']>;
  valueType: FormControl<AttachmentRefOrValueFormRawValue['valueType']>;
  schemaLocation: FormControl<AttachmentRefOrValueFormRawValue['schemaLocation']>;
  type: FormControl<AttachmentRefOrValueFormRawValue['type']>;
};

export type AttachmentRefOrValueFormGroup = FormGroup<AttachmentRefOrValueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AttachmentRefOrValueFormService {
  createAttachmentRefOrValueFormGroup(
    attachmentRefOrValue: AttachmentRefOrValueFormGroupInput = { id: null }
  ): AttachmentRefOrValueFormGroup {
    const attachmentRefOrValueRawValue = this.convertAttachmentRefOrValueToAttachmentRefOrValueRawValue({
      ...this.getFormDefaults(),
      ...attachmentRefOrValue,
    });
    return new FormGroup<AttachmentRefOrValueFormGroupContent>({
      attachmentType: new FormControl(attachmentRefOrValueRawValue.attachmentType),
      content: new FormControl(attachmentRefOrValueRawValue.content),
      description: new FormControl(attachmentRefOrValueRawValue.description),
      href: new FormControl(attachmentRefOrValueRawValue.href),
      id: new FormControl(
        { value: attachmentRefOrValueRawValue.id, disabled: attachmentRefOrValueRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      mimeType: new FormControl(attachmentRefOrValueRawValue.mimeType),
      name: new FormControl(attachmentRefOrValueRawValue.name),
      sizeOfBytes: new FormControl(attachmentRefOrValueRawValue.sizeOfBytes),
      url: new FormControl(attachmentRefOrValueRawValue.url),
      validForFrom: new FormControl(attachmentRefOrValueRawValue.validForFrom),
      validForTo: new FormControl(attachmentRefOrValueRawValue.validForTo),
      valueType: new FormControl(attachmentRefOrValueRawValue.valueType),
      schemaLocation: new FormControl(attachmentRefOrValueRawValue.schemaLocation),
      type: new FormControl(attachmentRefOrValueRawValue.type),
    });
  }

  getAttachmentRefOrValue(form: AttachmentRefOrValueFormGroup): IAttachmentRefOrValue | NewAttachmentRefOrValue {
    return this.convertAttachmentRefOrValueRawValueToAttachmentRefOrValue(
      form.getRawValue() as AttachmentRefOrValueFormRawValue | NewAttachmentRefOrValueFormRawValue
    );
  }

  resetForm(form: AttachmentRefOrValueFormGroup, attachmentRefOrValue: AttachmentRefOrValueFormGroupInput): void {
    const attachmentRefOrValueRawValue = this.convertAttachmentRefOrValueToAttachmentRefOrValueRawValue({
      ...this.getFormDefaults(),
      ...attachmentRefOrValue,
    });
    form.reset(
      {
        ...attachmentRefOrValueRawValue,
        id: { value: attachmentRefOrValueRawValue.id, disabled: attachmentRefOrValueRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AttachmentRefOrValueFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      validForFrom: currentTime,
      validForTo: currentTime,
    };
  }

  private convertAttachmentRefOrValueRawValueToAttachmentRefOrValue(
    rawAttachmentRefOrValue: AttachmentRefOrValueFormRawValue | NewAttachmentRefOrValueFormRawValue
  ): IAttachmentRefOrValue | NewAttachmentRefOrValue {
    return {
      ...rawAttachmentRefOrValue,
      validForFrom: dayjs(rawAttachmentRefOrValue.validForFrom, DATE_TIME_FORMAT),
      validForTo: dayjs(rawAttachmentRefOrValue.validForTo, DATE_TIME_FORMAT),
    };
  }

  private convertAttachmentRefOrValueToAttachmentRefOrValueRawValue(
    attachmentRefOrValue: IAttachmentRefOrValue | (Partial<NewAttachmentRefOrValue> & AttachmentRefOrValueFormDefaults)
  ): AttachmentRefOrValueFormRawValue | PartialWithRequiredKeyOf<NewAttachmentRefOrValueFormRawValue> {
    return {
      ...attachmentRefOrValue,
      validForFrom: attachmentRefOrValue.validForFrom ? attachmentRefOrValue.validForFrom.format(DATE_TIME_FORMAT) : undefined,
      validForTo: attachmentRefOrValue.validForTo ? attachmentRefOrValue.validForTo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
