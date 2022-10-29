import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITargetProductSchema, NewTargetProductSchema } from '../target-product-schema.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITargetProductSchema for edit and NewTargetProductSchemaFormGroupInput for create.
 */
type TargetProductSchemaFormGroupInput = ITargetProductSchema | PartialWithRequiredKeyOf<NewTargetProductSchema>;

type TargetProductSchemaFormDefaults = Pick<NewTargetProductSchema, 'id'>;

type TargetProductSchemaFormGroupContent = {
  id: FormControl<ITargetProductSchema['id'] | NewTargetProductSchema['id']>;
  schemaLocation: FormControl<ITargetProductSchema['schemaLocation']>;
  type: FormControl<ITargetProductSchema['type']>;
};

export type TargetProductSchemaFormGroup = FormGroup<TargetProductSchemaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TargetProductSchemaFormService {
  createTargetProductSchemaFormGroup(targetProductSchema: TargetProductSchemaFormGroupInput = { id: null }): TargetProductSchemaFormGroup {
    const targetProductSchemaRawValue = {
      ...this.getFormDefaults(),
      ...targetProductSchema,
    };
    return new FormGroup<TargetProductSchemaFormGroupContent>({
      id: new FormControl(
        { value: targetProductSchemaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      schemaLocation: new FormControl(targetProductSchemaRawValue.schemaLocation),
      type: new FormControl(targetProductSchemaRawValue.type),
    });
  }

  getTargetProductSchema(form: TargetProductSchemaFormGroup): ITargetProductSchema | NewTargetProductSchema {
    return form.getRawValue() as ITargetProductSchema | NewTargetProductSchema;
  }

  resetForm(form: TargetProductSchemaFormGroup, targetProductSchema: TargetProductSchemaFormGroupInput): void {
    const targetProductSchemaRawValue = { ...this.getFormDefaults(), ...targetProductSchema };
    form.reset(
      {
        ...targetProductSchemaRawValue,
        id: { value: targetProductSchemaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TargetProductSchemaFormDefaults {
    return {
      id: null,
    };
  }
}
