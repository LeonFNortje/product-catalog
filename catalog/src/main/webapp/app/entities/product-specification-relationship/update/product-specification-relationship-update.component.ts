import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import {
  ProductSpecificationRelationshipFormService,
  ProductSpecificationRelationshipFormGroup,
} from './product-specification-relationship-form.service';
import { IProductSpecificationRelationship } from '../product-specification-relationship.model';
import { ProductSpecificationRelationshipService } from '../service/product-specification-relationship.service';

@Component({
  selector: 'jhi-product-specification-relationship-update',
  templateUrl: './product-specification-relationship-update.component.html',
})
export class ProductSpecificationRelationshipUpdateComponent implements OnInit {
  isSaving = false;
  productSpecificationRelationship: IProductSpecificationRelationship | null = null;

  editForm: ProductSpecificationRelationshipFormGroup =
    this.productSpecificationRelationshipFormService.createProductSpecificationRelationshipFormGroup();

  constructor(
    protected productSpecificationRelationshipService: ProductSpecificationRelationshipService,
    protected productSpecificationRelationshipFormService: ProductSpecificationRelationshipFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationRelationship }) => {
      this.productSpecificationRelationship = productSpecificationRelationship;
      if (productSpecificationRelationship) {
        this.updateForm(productSpecificationRelationship);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSpecificationRelationship = this.productSpecificationRelationshipFormService.getProductSpecificationRelationship(
      this.editForm
    );
    if (productSpecificationRelationship.id !== null) {
      this.subscribeToSaveResponse(this.productSpecificationRelationshipService.update(productSpecificationRelationship));
    } else {
      this.subscribeToSaveResponse(this.productSpecificationRelationshipService.create(productSpecificationRelationship));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSpecificationRelationship>>): void {
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

  protected updateForm(productSpecificationRelationship: IProductSpecificationRelationship): void {
    this.productSpecificationRelationship = productSpecificationRelationship;
    this.productSpecificationRelationshipFormService.resetForm(this.editForm, productSpecificationRelationship);
  }
}
