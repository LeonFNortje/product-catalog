import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TargetProductSchemaFormService, TargetProductSchemaFormGroup } from './target-product-schema-form.service';
import { ITargetProductSchema } from '../target-product-schema.model';
import { TargetProductSchemaService } from '../service/target-product-schema.service';

@Component({
  selector: 'jhi-target-product-schema-update',
  templateUrl: './target-product-schema-update.component.html',
})
export class TargetProductSchemaUpdateComponent implements OnInit {
  isSaving = false;
  targetProductSchema: ITargetProductSchema | null = null;

  editForm: TargetProductSchemaFormGroup = this.targetProductSchemaFormService.createTargetProductSchemaFormGroup();

  constructor(
    protected targetProductSchemaService: TargetProductSchemaService,
    protected targetProductSchemaFormService: TargetProductSchemaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ targetProductSchema }) => {
      this.targetProductSchema = targetProductSchema;
      if (targetProductSchema) {
        this.updateForm(targetProductSchema);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const targetProductSchema = this.targetProductSchemaFormService.getTargetProductSchema(this.editForm);
    if (targetProductSchema.id !== null) {
      this.subscribeToSaveResponse(this.targetProductSchemaService.update(targetProductSchema));
    } else {
      this.subscribeToSaveResponse(this.targetProductSchemaService.create(targetProductSchema));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITargetProductSchema>>): void {
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

  protected updateForm(targetProductSchema: ITargetProductSchema): void {
    this.targetProductSchema = targetProductSchema;
    this.targetProductSchemaFormService.resetForm(this.editForm, targetProductSchema);
  }
}
