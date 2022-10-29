import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServiceSpecificationRef, NewServiceSpecificationRef } from '../service-specification-ref.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServiceSpecificationRef for edit and NewServiceSpecificationRefFormGroupInput for create.
 */
type ServiceSpecificationRefFormGroupInput = IServiceSpecificationRef | PartialWithRequiredKeyOf<NewServiceSpecificationRef>;

type ServiceSpecificationRefFormDefaults = Pick<NewServiceSpecificationRef, 'id'>;

type ServiceSpecificationRefFormGroupContent = {
  href: FormControl<IServiceSpecificationRef['href']>;
  id: FormControl<IServiceSpecificationRef['id'] | NewServiceSpecificationRef['id']>;
  name: FormControl<IServiceSpecificationRef['name']>;
  version: FormControl<IServiceSpecificationRef['version']>;
  schemaLocation: FormControl<IServiceSpecificationRef['schemaLocation']>;
  type: FormControl<IServiceSpecificationRef['type']>;
};

export type ServiceSpecificationRefFormGroup = FormGroup<ServiceSpecificationRefFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceSpecificationRefFormService {
  createServiceSpecificationRefFormGroup(
    serviceSpecificationRef: ServiceSpecificationRefFormGroupInput = { id: null }
  ): ServiceSpecificationRefFormGroup {
    const serviceSpecificationRefRawValue = {
      ...this.getFormDefaults(),
      ...serviceSpecificationRef,
    };
    return new FormGroup<ServiceSpecificationRefFormGroupContent>({
      href: new FormControl(serviceSpecificationRefRawValue.href),
      id: new FormControl(
        { value: serviceSpecificationRefRawValue.id, disabled: serviceSpecificationRefRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(serviceSpecificationRefRawValue.name),
      version: new FormControl(serviceSpecificationRefRawValue.version),
      schemaLocation: new FormControl(serviceSpecificationRefRawValue.schemaLocation),
      type: new FormControl(serviceSpecificationRefRawValue.type),
    });
  }

  getServiceSpecificationRef(form: ServiceSpecificationRefFormGroup): IServiceSpecificationRef | NewServiceSpecificationRef {
    return form.getRawValue() as IServiceSpecificationRef | NewServiceSpecificationRef;
  }

  resetForm(form: ServiceSpecificationRefFormGroup, serviceSpecificationRef: ServiceSpecificationRefFormGroupInput): void {
    const serviceSpecificationRefRawValue = { ...this.getFormDefaults(), ...serviceSpecificationRef };
    form.reset(
      {
        ...serviceSpecificationRefRawValue,
        id: { value: serviceSpecificationRefRawValue.id, disabled: serviceSpecificationRefRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ServiceSpecificationRefFormDefaults {
    return {
      id: null,
    };
  }
}
