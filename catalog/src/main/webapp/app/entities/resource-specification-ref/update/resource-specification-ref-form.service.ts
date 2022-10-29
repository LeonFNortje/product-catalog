import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IResourceSpecificationRef, NewResourceSpecificationRef } from '../resource-specification-ref.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResourceSpecificationRef for edit and NewResourceSpecificationRefFormGroupInput for create.
 */
type ResourceSpecificationRefFormGroupInput = IResourceSpecificationRef | PartialWithRequiredKeyOf<NewResourceSpecificationRef>;

type ResourceSpecificationRefFormDefaults = Pick<NewResourceSpecificationRef, 'id'>;

type ResourceSpecificationRefFormGroupContent = {
  href: FormControl<IResourceSpecificationRef['href']>;
  id: FormControl<IResourceSpecificationRef['id'] | NewResourceSpecificationRef['id']>;
  name: FormControl<IResourceSpecificationRef['name']>;
  version: FormControl<IResourceSpecificationRef['version']>;
  schemaLocation: FormControl<IResourceSpecificationRef['schemaLocation']>;
  type: FormControl<IResourceSpecificationRef['type']>;
};

export type ResourceSpecificationRefFormGroup = FormGroup<ResourceSpecificationRefFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResourceSpecificationRefFormService {
  createResourceSpecificationRefFormGroup(
    resourceSpecificationRef: ResourceSpecificationRefFormGroupInput = { id: null }
  ): ResourceSpecificationRefFormGroup {
    const resourceSpecificationRefRawValue = {
      ...this.getFormDefaults(),
      ...resourceSpecificationRef,
    };
    return new FormGroup<ResourceSpecificationRefFormGroupContent>({
      href: new FormControl(resourceSpecificationRefRawValue.href),
      id: new FormControl(
        { value: resourceSpecificationRefRawValue.id, disabled: resourceSpecificationRefRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(resourceSpecificationRefRawValue.name),
      version: new FormControl(resourceSpecificationRefRawValue.version),
      schemaLocation: new FormControl(resourceSpecificationRefRawValue.schemaLocation),
      type: new FormControl(resourceSpecificationRefRawValue.type),
    });
  }

  getResourceSpecificationRef(form: ResourceSpecificationRefFormGroup): IResourceSpecificationRef | NewResourceSpecificationRef {
    return form.getRawValue() as IResourceSpecificationRef | NewResourceSpecificationRef;
  }

  resetForm(form: ResourceSpecificationRefFormGroup, resourceSpecificationRef: ResourceSpecificationRefFormGroupInput): void {
    const resourceSpecificationRefRawValue = { ...this.getFormDefaults(), ...resourceSpecificationRef };
    form.reset(
      {
        ...resourceSpecificationRefRawValue,
        id: { value: resourceSpecificationRefRawValue.id, disabled: resourceSpecificationRefRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResourceSpecificationRefFormDefaults {
    return {
      id: null,
    };
  }
}
