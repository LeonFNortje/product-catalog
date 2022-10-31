import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRelatedParty, NewRelatedParty } from '../related-party.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRelatedParty for edit and NewRelatedPartyFormGroupInput for create.
 */
type RelatedPartyFormGroupInput = IRelatedParty | PartialWithRequiredKeyOf<NewRelatedParty>;

type RelatedPartyFormDefaults = Pick<NewRelatedParty, 'id'>;

type RelatedPartyFormGroupContent = {
  id: FormControl<IRelatedParty['id'] | NewRelatedParty['id']>;
  href: FormControl<IRelatedParty['href']>;
  name: FormControl<IRelatedParty['name']>;
  role: FormControl<IRelatedParty['role']>;
  schemaLocation: FormControl<IRelatedParty['schemaLocation']>;
  type: FormControl<IRelatedParty['type']>;
};

export type RelatedPartyFormGroup = FormGroup<RelatedPartyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RelatedPartyFormService {
  createRelatedPartyFormGroup(relatedParty: RelatedPartyFormGroupInput = { id: null }): RelatedPartyFormGroup {
    const relatedPartyRawValue = {
      ...this.getFormDefaults(),
      ...relatedParty,
    };
    return new FormGroup<RelatedPartyFormGroupContent>({
      id: new FormControl(
        { value: relatedPartyRawValue.id, disabled: relatedPartyRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      href: new FormControl(relatedPartyRawValue.href),
      name: new FormControl(relatedPartyRawValue.name),
      role: new FormControl(relatedPartyRawValue.role),
      schemaLocation: new FormControl(relatedPartyRawValue.schemaLocation),
      type: new FormControl(relatedPartyRawValue.type),
    });
  }

  getRelatedParty(form: RelatedPartyFormGroup): IRelatedParty | NewRelatedParty {
    return form.getRawValue() as IRelatedParty | NewRelatedParty;
  }

  resetForm(form: RelatedPartyFormGroup, relatedParty: RelatedPartyFormGroupInput): void {
    const relatedPartyRawValue = { ...this.getFormDefaults(), ...relatedParty };
    form.reset(
      {
        ...relatedPartyRawValue,
        id: { value: relatedPartyRawValue.id, disabled: relatedPartyRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RelatedPartyFormDefaults {
    return {
      id: null,
    };
  }
}
