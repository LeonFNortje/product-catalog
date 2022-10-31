import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RelatedPlaceFormService, RelatedPlaceFormGroup } from './related-place-form.service';
import { IRelatedPlace } from '../related-place.model';
import { RelatedPlaceService } from '../service/related-place.service';

@Component({
  selector: 'jhi-related-place-update',
  templateUrl: './related-place-update.component.html',
})
export class RelatedPlaceUpdateComponent implements OnInit {
  isSaving = false;
  relatedPlace: IRelatedPlace | null = null;

  editForm: RelatedPlaceFormGroup = this.relatedPlaceFormService.createRelatedPlaceFormGroup();

  constructor(
    protected relatedPlaceService: RelatedPlaceService,
    protected relatedPlaceFormService: RelatedPlaceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relatedPlace }) => {
      this.relatedPlace = relatedPlace;
      if (relatedPlace) {
        this.updateForm(relatedPlace);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relatedPlace = this.relatedPlaceFormService.getRelatedPlace(this.editForm);
    if (relatedPlace.id !== null) {
      this.subscribeToSaveResponse(this.relatedPlaceService.update(relatedPlace));
    } else {
      this.subscribeToSaveResponse(this.relatedPlaceService.create(relatedPlace));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelatedPlace>>): void {
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

  protected updateForm(relatedPlace: IRelatedPlace): void {
    this.relatedPlace = relatedPlace;
    this.relatedPlaceFormService.resetForm(this.editForm, relatedPlace);
  }
}
