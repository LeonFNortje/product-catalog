import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BundledProductSpecificationFormService, BundledProductSpecificationFormGroup } from './bundled-product-specification-form.service';
import { IBundledProductSpecification } from '../bundled-product-specification.model';
import { BundledProductSpecificationService } from '../service/bundled-product-specification.service';

@Component({
  selector: 'jhi-bundled-product-specification-update',
  templateUrl: './bundled-product-specification-update.component.html',
})
export class BundledProductSpecificationUpdateComponent implements OnInit {
  isSaving = false;
  bundledProductSpecification: IBundledProductSpecification | null = null;

  editForm: BundledProductSpecificationFormGroup = this.bundledProductSpecificationFormService.createBundledProductSpecificationFormGroup();

  constructor(
    protected bundledProductSpecificationService: BundledProductSpecificationService,
    protected bundledProductSpecificationFormService: BundledProductSpecificationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bundledProductSpecification }) => {
      this.bundledProductSpecification = bundledProductSpecification;
      if (bundledProductSpecification) {
        this.updateForm(bundledProductSpecification);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bundledProductSpecification = this.bundledProductSpecificationFormService.getBundledProductSpecification(this.editForm);
    if (bundledProductSpecification.id !== null) {
      this.subscribeToSaveResponse(this.bundledProductSpecificationService.update(bundledProductSpecification));
    } else {
      this.subscribeToSaveResponse(this.bundledProductSpecificationService.create(bundledProductSpecification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBundledProductSpecification>>): void {
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

  protected updateForm(bundledProductSpecification: IBundledProductSpecification): void {
    this.bundledProductSpecification = bundledProductSpecification;
    this.bundledProductSpecificationFormService.resetForm(this.editForm, bundledProductSpecification);
  }
}
