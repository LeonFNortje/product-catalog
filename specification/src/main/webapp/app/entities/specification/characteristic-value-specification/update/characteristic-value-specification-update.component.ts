import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  CharacteristicValueSpecificationFormService,
  CharacteristicValueSpecificationFormGroup,
} from './characteristic-value-specification-form.service';
import { ICharacteristicValueSpecification } from '../characteristic-value-specification.model';
import { CharacteristicValueSpecificationService } from '../service/characteristic-value-specification.service';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/specification/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from 'app/entities/specification/product-specification-characteristic-relationship/service/product-specification-characteristic-relationship.service';

@Component({
  selector: 'jhi-characteristic-value-specification-update',
  templateUrl: './characteristic-value-specification-update.component.html',
})
export class CharacteristicValueSpecificationUpdateComponent implements OnInit {
  isSaving = false;
  characteristicValueSpecification: ICharacteristicValueSpecification | null = null;

  productSpecificationCharacteristicRelationshipsSharedCollection: IProductSpecificationCharacteristicRelationship[] = [];

  editForm: CharacteristicValueSpecificationFormGroup =
    this.characteristicValueSpecificationFormService.createCharacteristicValueSpecificationFormGroup();

  constructor(
    protected characteristicValueSpecificationService: CharacteristicValueSpecificationService,
    protected characteristicValueSpecificationFormService: CharacteristicValueSpecificationFormService,
    protected productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProductSpecificationCharacteristicRelationship = (
    o1: IProductSpecificationCharacteristicRelationship | null,
    o2: IProductSpecificationCharacteristicRelationship | null
  ): boolean => this.productSpecificationCharacteristicRelationshipService.compareProductSpecificationCharacteristicRelationship(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ characteristicValueSpecification }) => {
      this.characteristicValueSpecification = characteristicValueSpecification;
      if (characteristicValueSpecification) {
        this.updateForm(characteristicValueSpecification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const characteristicValueSpecification = this.characteristicValueSpecificationFormService.getCharacteristicValueSpecification(
      this.editForm
    );
    if (characteristicValueSpecification.id !== null) {
      this.subscribeToSaveResponse(this.characteristicValueSpecificationService.update(characteristicValueSpecification));
    } else {
      this.subscribeToSaveResponse(this.characteristicValueSpecificationService.create(characteristicValueSpecification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICharacteristicValueSpecification>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(characteristicValueSpecification: ICharacteristicValueSpecification): void {
    this.characteristicValueSpecification = characteristicValueSpecification;
    this.characteristicValueSpecificationFormService.resetForm(this.editForm, characteristicValueSpecification);

    this.productSpecificationCharacteristicRelationshipsSharedCollection =
      this.productSpecificationCharacteristicRelationshipService.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing<IProductSpecificationCharacteristicRelationship>(
        this.productSpecificationCharacteristicRelationshipsSharedCollection,
        characteristicValueSpecification.productSpecificationCharacteristicRelationship
      );
  }

  protected loadRelationshipsOptions(): void {
    this.productSpecificationCharacteristicRelationshipService
      .query()
      .pipe(map((res: HttpResponse<IProductSpecificationCharacteristicRelationship[]>) => res.body ?? []))
      .pipe(
        map((productSpecificationCharacteristicRelationships: IProductSpecificationCharacteristicRelationship[]) =>
          this.productSpecificationCharacteristicRelationshipService.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing<IProductSpecificationCharacteristicRelationship>(
            productSpecificationCharacteristicRelationships,
            this.characteristicValueSpecification?.productSpecificationCharacteristicRelationship
          )
        )
      )
      .subscribe(
        (productSpecificationCharacteristicRelationships: IProductSpecificationCharacteristicRelationship[]) =>
          (this.productSpecificationCharacteristicRelationshipsSharedCollection = productSpecificationCharacteristicRelationships)
      );
  }
}
