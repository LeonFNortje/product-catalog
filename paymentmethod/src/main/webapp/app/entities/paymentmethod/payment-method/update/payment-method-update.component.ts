import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaymentMethodFormService, PaymentMethodFormGroup } from './payment-method-form.service';
import { IPaymentMethod } from '../payment-method.model';
import { PaymentMethodService } from '../service/payment-method.service';
import { IRelatedPlace } from 'app/entities/paymentmethod/related-place/related-place.model';
import { RelatedPlaceService } from 'app/entities/paymentmethod/related-place/service/related-place.service';
import { IRelatedParty } from 'app/entities/paymentmethod/related-party/related-party.model';
import { RelatedPartyService } from 'app/entities/paymentmethod/related-party/service/related-party.service';

@Component({
  selector: 'jhi-payment-method-update',
  templateUrl: './payment-method-update.component.html',
})
export class PaymentMethodUpdateComponent implements OnInit {
  isSaving = false;
  paymentMethod: IPaymentMethod | null = null;

  relatedPlacesSharedCollection: IRelatedPlace[] = [];
  relatedPartiesSharedCollection: IRelatedParty[] = [];

  editForm: PaymentMethodFormGroup = this.paymentMethodFormService.createPaymentMethodFormGroup();

  constructor(
    protected paymentMethodService: PaymentMethodService,
    protected paymentMethodFormService: PaymentMethodFormService,
    protected relatedPlaceService: RelatedPlaceService,
    protected relatedPartyService: RelatedPartyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRelatedPlace = (o1: IRelatedPlace | null, o2: IRelatedPlace | null): boolean =>
    this.relatedPlaceService.compareRelatedPlace(o1, o2);

  compareRelatedParty = (o1: IRelatedParty | null, o2: IRelatedParty | null): boolean =>
    this.relatedPartyService.compareRelatedParty(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethod }) => {
      this.paymentMethod = paymentMethod;
      if (paymentMethod) {
        this.updateForm(paymentMethod);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethod = this.paymentMethodFormService.getPaymentMethod(this.editForm);
    if (paymentMethod.id !== null) {
      this.subscribeToSaveResponse(this.paymentMethodService.update(paymentMethod));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodService.create(paymentMethod));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethod>>): void {
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

  protected updateForm(paymentMethod: IPaymentMethod): void {
    this.paymentMethod = paymentMethod;
    this.paymentMethodFormService.resetForm(this.editForm, paymentMethod);

    this.relatedPlacesSharedCollection = this.relatedPlaceService.addRelatedPlaceToCollectionIfMissing<IRelatedPlace>(
      this.relatedPlacesSharedCollection,
      paymentMethod.relatedPlace
    );
    this.relatedPartiesSharedCollection = this.relatedPartyService.addRelatedPartyToCollectionIfMissing<IRelatedParty>(
      this.relatedPartiesSharedCollection,
      paymentMethod.relatedParty
    );
  }

  protected loadRelationshipsOptions(): void {
    this.relatedPlaceService
      .query()
      .pipe(map((res: HttpResponse<IRelatedPlace[]>) => res.body ?? []))
      .pipe(
        map((relatedPlaces: IRelatedPlace[]) =>
          this.relatedPlaceService.addRelatedPlaceToCollectionIfMissing<IRelatedPlace>(relatedPlaces, this.paymentMethod?.relatedPlace)
        )
      )
      .subscribe((relatedPlaces: IRelatedPlace[]) => (this.relatedPlacesSharedCollection = relatedPlaces));

    this.relatedPartyService
      .query()
      .pipe(map((res: HttpResponse<IRelatedParty[]>) => res.body ?? []))
      .pipe(
        map((relatedParties: IRelatedParty[]) =>
          this.relatedPartyService.addRelatedPartyToCollectionIfMissing<IRelatedParty>(relatedParties, this.paymentMethod?.relatedParty)
        )
      )
      .subscribe((relatedParties: IRelatedParty[]) => (this.relatedPartiesSharedCollection = relatedParties));
  }
}
