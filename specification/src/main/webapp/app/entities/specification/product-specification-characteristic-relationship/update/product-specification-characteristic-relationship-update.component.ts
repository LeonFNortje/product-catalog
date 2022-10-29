import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import {
  ProductSpecificationCharacteristicRelationshipFormService,
  ProductSpecificationCharacteristicRelationshipFormGroup,
} from './product-specification-characteristic-relationship-form.service';
import { IProductSpecificationCharacteristicRelationship } from '../product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from '../service/product-specification-characteristic-relationship.service';

@Component({
  selector: 'jhi-product-specification-characteristic-relationship-update',
  templateUrl: './product-specification-characteristic-relationship-update.component.html',
})
export class ProductSpecificationCharacteristicRelationshipUpdateComponent implements OnInit {
  isSaving = false;
  productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship | null = null;

  editForm: ProductSpecificationCharacteristicRelationshipFormGroup =
    this.productSpecificationCharacteristicRelationshipFormService.createProductSpecificationCharacteristicRelationshipFormGroup();

  constructor(
    protected productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService,
    protected productSpecificationCharacteristicRelationshipFormService: ProductSpecificationCharacteristicRelationshipFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationCharacteristicRelationship }) => {
      this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
      if (productSpecificationCharacteristicRelationship) {
        this.updateForm(productSpecificationCharacteristicRelationship);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSpecificationCharacteristicRelationship =
      this.productSpecificationCharacteristicRelationshipFormService.getProductSpecificationCharacteristicRelationship(this.editForm);
    if (productSpecificationCharacteristicRelationship.id !== null) {
      this.subscribeToSaveResponse(
        this.productSpecificationCharacteristicRelationshipService.update(productSpecificationCharacteristicRelationship)
      );
    } else {
      this.subscribeToSaveResponse(
        this.productSpecificationCharacteristicRelationshipService.create(productSpecificationCharacteristicRelationship)
      );
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSpecificationCharacteristicRelationship>>): void {
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

  protected updateForm(productSpecificationCharacteristicRelationship: IProductSpecificationCharacteristicRelationship): void {
    this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
    this.productSpecificationCharacteristicRelationshipFormService.resetForm(this.editForm, productSpecificationCharacteristicRelationship);
  }
}
