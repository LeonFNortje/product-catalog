import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  ProductSpecificationCharacteristicFormService,
  ProductSpecificationCharacteristicFormGroup,
} from './product-specification-characteristic-form.service';
import { IProductSpecificationCharacteristic } from '../product-specification-characteristic.model';
import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';
import { IProductSpecificationCharacteristicRelationship } from 'app/entities/product-specification-characteristic-relationship/product-specification-characteristic-relationship.model';
import { ProductSpecificationCharacteristicRelationshipService } from 'app/entities/product-specification-characteristic-relationship/service/product-specification-characteristic-relationship.service';

@Component({
  selector: 'jhi-product-specification-characteristic-update',
  templateUrl: './product-specification-characteristic-update.component.html',
})
export class ProductSpecificationCharacteristicUpdateComponent implements OnInit {
  isSaving = false;
  productSpecificationCharacteristic: IProductSpecificationCharacteristic | null = null;

  productSpecificationCharacteristicRelationshipsSharedCollection: IProductSpecificationCharacteristicRelationship[] = [];

  editForm: ProductSpecificationCharacteristicFormGroup =
    this.productSpecificationCharacteristicFormService.createProductSpecificationCharacteristicFormGroup();

  constructor(
    protected productSpecificationCharacteristicService: ProductSpecificationCharacteristicService,
    protected productSpecificationCharacteristicFormService: ProductSpecificationCharacteristicFormService,
    protected productSpecificationCharacteristicRelationshipService: ProductSpecificationCharacteristicRelationshipService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProductSpecificationCharacteristicRelationship = (
    o1: IProductSpecificationCharacteristicRelationship | null,
    o2: IProductSpecificationCharacteristicRelationship | null
  ): boolean => this.productSpecificationCharacteristicRelationshipService.compareProductSpecificationCharacteristicRelationship(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSpecificationCharacteristic }) => {
      this.productSpecificationCharacteristic = productSpecificationCharacteristic;
      if (productSpecificationCharacteristic) {
        this.updateForm(productSpecificationCharacteristic);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSpecificationCharacteristic = this.productSpecificationCharacteristicFormService.getProductSpecificationCharacteristic(
      this.editForm
    );
    if (productSpecificationCharacteristic.id !== null) {
      this.subscribeToSaveResponse(this.productSpecificationCharacteristicService.update(productSpecificationCharacteristic));
    } else {
      this.subscribeToSaveResponse(this.productSpecificationCharacteristicService.create(productSpecificationCharacteristic));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSpecificationCharacteristic>>): void {
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

  protected updateForm(productSpecificationCharacteristic: IProductSpecificationCharacteristic): void {
    this.productSpecificationCharacteristic = productSpecificationCharacteristic;
    this.productSpecificationCharacteristicFormService.resetForm(this.editForm, productSpecificationCharacteristic);

    this.productSpecificationCharacteristicRelationshipsSharedCollection =
      this.productSpecificationCharacteristicRelationshipService.addProductSpecificationCharacteristicRelationshipToCollectionIfMissing<IProductSpecificationCharacteristicRelationship>(
        this.productSpecificationCharacteristicRelationshipsSharedCollection,
        productSpecificationCharacteristic.productSpecificationCharacteristicRelationship
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
            this.productSpecificationCharacteristic?.productSpecificationCharacteristicRelationship
          )
        )
      )
      .subscribe(
        (productSpecificationCharacteristicRelationships: IProductSpecificationCharacteristicRelationship[]) =>
          (this.productSpecificationCharacteristicRelationshipsSharedCollection = productSpecificationCharacteristicRelationships)
      );
  }
}
