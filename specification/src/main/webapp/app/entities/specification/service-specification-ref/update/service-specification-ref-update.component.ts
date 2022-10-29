import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ServiceSpecificationRefFormService, ServiceSpecificationRefFormGroup } from './service-specification-ref-form.service';
import { IServiceSpecificationRef } from '../service-specification-ref.model';
import { ServiceSpecificationRefService } from '../service/service-specification-ref.service';

@Component({
  selector: 'jhi-service-specification-ref-update',
  templateUrl: './service-specification-ref-update.component.html',
})
export class ServiceSpecificationRefUpdateComponent implements OnInit {
  isSaving = false;
  serviceSpecificationRef: IServiceSpecificationRef | null = null;

  editForm: ServiceSpecificationRefFormGroup = this.serviceSpecificationRefFormService.createServiceSpecificationRefFormGroup();

  constructor(
    protected serviceSpecificationRefService: ServiceSpecificationRefService,
    protected serviceSpecificationRefFormService: ServiceSpecificationRefFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceSpecificationRef }) => {
      this.serviceSpecificationRef = serviceSpecificationRef;
      if (serviceSpecificationRef) {
        this.updateForm(serviceSpecificationRef);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceSpecificationRef = this.serviceSpecificationRefFormService.getServiceSpecificationRef(this.editForm);
    if (serviceSpecificationRef.id !== null) {
      this.subscribeToSaveResponse(this.serviceSpecificationRefService.update(serviceSpecificationRef));
    } else {
      this.subscribeToSaveResponse(this.serviceSpecificationRefService.create(serviceSpecificationRef));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceSpecificationRef>>): void {
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

  protected updateForm(serviceSpecificationRef: IServiceSpecificationRef): void {
    this.serviceSpecificationRef = serviceSpecificationRef;
    this.serviceSpecificationRefFormService.resetForm(this.editForm, serviceSpecificationRef);
  }
}
