import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ResourceSpecificationRefFormService, ResourceSpecificationRefFormGroup } from './resource-specification-ref-form.service';
import { IResourceSpecificationRef } from '../resource-specification-ref.model';
import { ResourceSpecificationRefService } from '../service/resource-specification-ref.service';

@Component({
  selector: 'jhi-resource-specification-ref-update',
  templateUrl: './resource-specification-ref-update.component.html',
})
export class ResourceSpecificationRefUpdateComponent implements OnInit {
  isSaving = false;
  resourceSpecificationRef: IResourceSpecificationRef | null = null;

  editForm: ResourceSpecificationRefFormGroup = this.resourceSpecificationRefFormService.createResourceSpecificationRefFormGroup();

  constructor(
    protected resourceSpecificationRefService: ResourceSpecificationRefService,
    protected resourceSpecificationRefFormService: ResourceSpecificationRefFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resourceSpecificationRef }) => {
      this.resourceSpecificationRef = resourceSpecificationRef;
      if (resourceSpecificationRef) {
        this.updateForm(resourceSpecificationRef);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resourceSpecificationRef = this.resourceSpecificationRefFormService.getResourceSpecificationRef(this.editForm);
    if (resourceSpecificationRef.id !== null) {
      this.subscribeToSaveResponse(this.resourceSpecificationRefService.update(resourceSpecificationRef));
    } else {
      this.subscribeToSaveResponse(this.resourceSpecificationRefService.create(resourceSpecificationRef));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResourceSpecificationRef>>): void {
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

  protected updateForm(resourceSpecificationRef: IResourceSpecificationRef): void {
    this.resourceSpecificationRef = resourceSpecificationRef;
    this.resourceSpecificationRefFormService.resetForm(this.editForm, resourceSpecificationRef);
  }
}
