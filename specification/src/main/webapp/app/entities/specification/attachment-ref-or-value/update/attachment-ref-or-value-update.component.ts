import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AttachmentRefOrValueFormService, AttachmentRefOrValueFormGroup } from './attachment-ref-or-value-form.service';
import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';
import { AttachmentRefOrValueService } from '../service/attachment-ref-or-value.service';

@Component({
  selector: 'jhi-attachment-ref-or-value-update',
  templateUrl: './attachment-ref-or-value-update.component.html',
})
export class AttachmentRefOrValueUpdateComponent implements OnInit {
  isSaving = false;
  attachmentRefOrValue: IAttachmentRefOrValue | null = null;

  editForm: AttachmentRefOrValueFormGroup = this.attachmentRefOrValueFormService.createAttachmentRefOrValueFormGroup();

  constructor(
    protected attachmentRefOrValueService: AttachmentRefOrValueService,
    protected attachmentRefOrValueFormService: AttachmentRefOrValueFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachmentRefOrValue }) => {
      this.attachmentRefOrValue = attachmentRefOrValue;
      if (attachmentRefOrValue) {
        this.updateForm(attachmentRefOrValue);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attachmentRefOrValue = this.attachmentRefOrValueFormService.getAttachmentRefOrValue(this.editForm);
    if (attachmentRefOrValue.id !== null) {
      this.subscribeToSaveResponse(this.attachmentRefOrValueService.update(attachmentRefOrValue));
    } else {
      this.subscribeToSaveResponse(this.attachmentRefOrValueService.create(attachmentRefOrValue));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachmentRefOrValue>>): void {
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

  protected updateForm(attachmentRefOrValue: IAttachmentRefOrValue): void {
    this.attachmentRefOrValue = attachmentRefOrValue;
    this.attachmentRefOrValueFormService.resetForm(this.editForm, attachmentRefOrValue);
  }
}
