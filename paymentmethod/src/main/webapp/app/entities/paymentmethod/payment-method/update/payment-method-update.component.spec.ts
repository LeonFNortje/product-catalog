import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentMethodFormService } from './payment-method-form.service';
import { PaymentMethodService } from '../service/payment-method.service';
import { IPaymentMethod } from '../payment-method.model';
import { IRelatedPlace } from 'app/entities/paymentmethod/related-place/related-place.model';
import { RelatedPlaceService } from 'app/entities/paymentmethod/related-place/service/related-place.service';
import { IRelatedParty } from 'app/entities/paymentmethod/related-party/related-party.model';
import { RelatedPartyService } from 'app/entities/paymentmethod/related-party/service/related-party.service';

import { PaymentMethodUpdateComponent } from './payment-method-update.component';

describe('PaymentMethod Management Update Component', () => {
  let comp: PaymentMethodUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodFormService: PaymentMethodFormService;
  let paymentMethodService: PaymentMethodService;
  let relatedPlaceService: RelatedPlaceService;
  let relatedPartyService: RelatedPartyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentMethodUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PaymentMethodUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentMethodFormService = TestBed.inject(PaymentMethodFormService);
    paymentMethodService = TestBed.inject(PaymentMethodService);
    relatedPlaceService = TestBed.inject(RelatedPlaceService);
    relatedPartyService = TestBed.inject(RelatedPartyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RelatedPlace query and add missing value', () => {
      const paymentMethod: IPaymentMethod = { id: 'CBA' };
      const relatedPlace: IRelatedPlace = { id: 'ad924590-b676-4506-8ee8-40b003513ca3' };
      paymentMethod.relatedPlace = relatedPlace;

      const relatedPlaceCollection: IRelatedPlace[] = [{ id: '4edf4c12-0d8a-45f2-a478-6fc82ec675dc' }];
      jest.spyOn(relatedPlaceService, 'query').mockReturnValue(of(new HttpResponse({ body: relatedPlaceCollection })));
      const additionalRelatedPlaces = [relatedPlace];
      const expectedCollection: IRelatedPlace[] = [...additionalRelatedPlaces, ...relatedPlaceCollection];
      jest.spyOn(relatedPlaceService, 'addRelatedPlaceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(relatedPlaceService.query).toHaveBeenCalled();
      expect(relatedPlaceService.addRelatedPlaceToCollectionIfMissing).toHaveBeenCalledWith(
        relatedPlaceCollection,
        ...additionalRelatedPlaces.map(expect.objectContaining)
      );
      expect(comp.relatedPlacesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RelatedParty query and add missing value', () => {
      const paymentMethod: IPaymentMethod = { id: 'CBA' };
      const relatedParty: IRelatedParty = { id: '1a377291-2738-4fb3-be9a-cedb7013ca9f' };
      paymentMethod.relatedParty = relatedParty;

      const relatedPartyCollection: IRelatedParty[] = [{ id: '409f4afe-2301-4a38-b6b6-d32b53868bfe' }];
      jest.spyOn(relatedPartyService, 'query').mockReturnValue(of(new HttpResponse({ body: relatedPartyCollection })));
      const additionalRelatedParties = [relatedParty];
      const expectedCollection: IRelatedParty[] = [...additionalRelatedParties, ...relatedPartyCollection];
      jest.spyOn(relatedPartyService, 'addRelatedPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(relatedPartyService.query).toHaveBeenCalled();
      expect(relatedPartyService.addRelatedPartyToCollectionIfMissing).toHaveBeenCalledWith(
        relatedPartyCollection,
        ...additionalRelatedParties.map(expect.objectContaining)
      );
      expect(comp.relatedPartiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentMethod: IPaymentMethod = { id: 'CBA' };
      const relatedPlace: IRelatedPlace = { id: '9fde536c-1a2e-4c5f-b774-2bab7e27f04d' };
      paymentMethod.relatedPlace = relatedPlace;
      const relatedParty: IRelatedParty = { id: '94c286f6-2375-44d0-95de-563d3d241984' };
      paymentMethod.relatedParty = relatedParty;

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(comp.relatedPlacesSharedCollection).toContain(relatedPlace);
      expect(comp.relatedPartiesSharedCollection).toContain(relatedParty);
      expect(comp.paymentMethod).toEqual(paymentMethod);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 'ABC' };
      jest.spyOn(paymentMethodFormService, 'getPaymentMethod').mockReturnValue(paymentMethod);
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodFormService.getPaymentMethod).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentMethodService.update).toHaveBeenCalledWith(expect.objectContaining(paymentMethod));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 'ABC' };
      jest.spyOn(paymentMethodFormService, 'getPaymentMethod').mockReturnValue({ id: null });
      jest.spyOn(paymentMethodService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodFormService.getPaymentMethod).toHaveBeenCalled();
      expect(paymentMethodService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 'ABC' };
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentMethodService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRelatedPlace', () => {
      it('Should forward to relatedPlaceService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(relatedPlaceService, 'compareRelatedPlace');
        comp.compareRelatedPlace(entity, entity2);
        expect(relatedPlaceService.compareRelatedPlace).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRelatedParty', () => {
      it('Should forward to relatedPartyService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(relatedPartyService, 'compareRelatedParty');
        comp.compareRelatedParty(entity, entity2);
        expect(relatedPartyService.compareRelatedParty).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
