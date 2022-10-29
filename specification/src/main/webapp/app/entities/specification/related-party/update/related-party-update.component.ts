import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RelatedPartyFormService, RelatedPartyFormGroup } from './related-party-form.service';
import { IRelatedParty } from '../related-party.model';
import { RelatedPartyService } from '../service/related-party.service';

@Component({
  selector: 'jhi-related-party-update',
  templateUrl: './related-party-update.component.html',
})
export class RelatedPartyUpdateComponent implements OnInit {
  isSaving = false;
  relatedParty: IRelatedParty | null = null;

  editForm: RelatedPartyFormGroup = this.relatedPartyFormService.createRelatedPartyFormGroup();

  constructor(
    protected relatedPartyService: RelatedPartyService,
    protected relatedPartyFormService: RelatedPartyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relatedParty }) => {
      this.relatedParty = relatedParty;
      if (relatedParty) {
        this.updateForm(relatedParty);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relatedParty = this.relatedPartyFormService.getRelatedParty(this.editForm);
    if (relatedParty.id !== null) {
      this.subscribeToSaveResponse(this.relatedPartyService.update(relatedParty));
    } else {
      this.subscribeToSaveResponse(this.relatedPartyService.create(relatedParty));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelatedParty>>): void {
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

  protected updateForm(relatedParty: IRelatedParty): void {
    this.relatedParty = relatedParty;
    this.relatedPartyFormService.resetForm(this.editForm, relatedParty);
  }
}
