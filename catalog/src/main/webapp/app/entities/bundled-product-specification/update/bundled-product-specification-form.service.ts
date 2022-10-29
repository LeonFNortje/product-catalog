import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBundledProductSpecification, NewBundledProductSpecification } from '../bundled-product-specification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBundledProductSpecification for edit and NewBundledProductSpecificationFormGroupInput for create.
 */
type BundledProductSpecificationFormGroupInput = IBundledProductSpecification | PartialWithRequiredKeyOf<NewBundledProductSpecification>;

type BundledProductSpecificationFormDefaults = Pick<NewBundledProductSpecification, 'id'>;

type BundledProductSpecificationFormGroupContent = {
  href: FormControl<IBundledProductSpecification['href']>;
  id: FormControl<IBundledProductSpecification['id'] | NewBundledProductSpecification['id']>;
  name: FormControl<IBundledProductSpecification['name']>;
  lifecycleStatus: FormControl<IBundledProductSpecification['lifecycleStatus']>;
  schemaLocation: FormControl<IBundledProductSpecification['schemaLocation']>;
  type: FormControl<IBundledProductSpecification['type']>;
};

export type BundledProductSpecificationFormGroup = FormGroup<BundledProductSpecificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BundledProductSpecificationFormService {
  createBundledProductSpecificationFormGroup(
    bundledProductSpecification: BundledProductSpecificationFormGroupInput = { id: null }
  ): BundledProductSpecificationFormGroup {
    const bundledProductSpecificationRawValue = {
      ...this.getFormDefaults(),
      ...bundledProductSpecification,
    };
    return new FormGroup<BundledProductSpecificationFormGroupContent>({
      href: new FormControl(bundledProductSpecificationRawValue.href),
      id: new FormControl(
        { value: bundledProductSpecificationRawValue.id, disabled: bundledProductSpecificationRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(bundledProductSpecificationRawValue.name),
      lifecycleStatus: new FormControl(bundledProductSpecificationRawValue.lifecycleStatus),
      schemaLocation: new FormControl(bundledProductSpecificationRawValue.schemaLocation),
      type: new FormControl(bundledProductSpecificationRawValue.type),
    });
  }

  getBundledProductSpecification(
    form: BundledProductSpecificationFormGroup
  ): IBundledProductSpecification | NewBundledProductSpecification {
    return form.getRawValue() as IBundledProductSpecification | NewBundledProductSpecification;
  }

  resetForm(form: BundledProductSpecificationFormGroup, bundledProductSpecification: BundledProductSpecificationFormGroupInput): void {
    const bundledProductSpecificationRawValue = { ...this.getFormDefaults(), ...bundledProductSpecification };
    form.reset(
      {
        ...bundledProductSpecificationRawValue,
        id: { value: bundledProductSpecificationRawValue.id, disabled: bundledProductSpecificationRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BundledProductSpecificationFormDefaults {
    return {
      id: null,
    };
  }
}
