import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRelatedPlace, NewRelatedPlace } from '../related-place.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRelatedPlace for edit and NewRelatedPlaceFormGroupInput for create.
 */
type RelatedPlaceFormGroupInput = IRelatedPlace | PartialWithRequiredKeyOf<NewRelatedPlace>;

type RelatedPlaceFormDefaults = Pick<NewRelatedPlace, 'id'>;

type RelatedPlaceFormGroupContent = {
  id: FormControl<IRelatedPlace['id'] | NewRelatedPlace['id']>;
  href: FormControl<IRelatedPlace['href']>;
  name: FormControl<IRelatedPlace['name']>;
  role: FormControl<IRelatedPlace['role']>;
  schemaLocation: FormControl<IRelatedPlace['schemaLocation']>;
  type: FormControl<IRelatedPlace['type']>;
};

export type RelatedPlaceFormGroup = FormGroup<RelatedPlaceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RelatedPlaceFormService {
  createRelatedPlaceFormGroup(relatedPlace: RelatedPlaceFormGroupInput = { id: null }): RelatedPlaceFormGroup {
    const relatedPlaceRawValue = {
      ...this.getFormDefaults(),
      ...relatedPlace,
    };
    return new FormGroup<RelatedPlaceFormGroupContent>({
      id: new FormControl(
        { value: relatedPlaceRawValue.id, disabled: relatedPlaceRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      href: new FormControl(relatedPlaceRawValue.href),
      name: new FormControl(relatedPlaceRawValue.name),
      role: new FormControl(relatedPlaceRawValue.role),
      schemaLocation: new FormControl(relatedPlaceRawValue.schemaLocation),
      type: new FormControl(relatedPlaceRawValue.type),
    });
  }

  getRelatedPlace(form: RelatedPlaceFormGroup): IRelatedPlace | NewRelatedPlace {
    return form.getRawValue() as IRelatedPlace | NewRelatedPlace;
  }

  resetForm(form: RelatedPlaceFormGroup, relatedPlace: RelatedPlaceFormGroupInput): void {
    const relatedPlaceRawValue = { ...this.getFormDefaults(), ...relatedPlace };
    form.reset(
      {
        ...relatedPlaceRawValue,
        id: { value: relatedPlaceRawValue.id, disabled: relatedPlaceRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RelatedPlaceFormDefaults {
    return {
      id: null,
    };
  }
}
