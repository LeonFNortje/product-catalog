import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RelatedPartyFormService } from './related-party-form.service';
import { RelatedPartyService } from '../service/related-party.service';
import { IRelatedParty } from '../related-party.model';

import { RelatedPartyUpdateComponent } from './related-party-update.component';

describe('RelatedParty Management Update Component', () => {
  let comp: RelatedPartyUpdateComponent;
  let fixture: ComponentFixture<RelatedPartyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let relatedPartyFormService: RelatedPartyFormService;
  let relatedPartyService: RelatedPartyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RelatedPartyUpdateComponent],
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
      .overrideTemplate(RelatedPartyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RelatedPartyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    relatedPartyFormService = TestBed.inject(RelatedPartyFormService);
    relatedPartyService = TestBed.inject(RelatedPartyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const relatedParty: IRelatedParty = { id: 'CBA' };

      activatedRoute.data = of({ relatedParty });
      comp.ngOnInit();

      expect(comp.relatedParty).toEqual(relatedParty);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedParty>>();
      const relatedParty = { id: 'ABC' };
      jest.spyOn(relatedPartyFormService, 'getRelatedParty').mockReturnValue(relatedParty);
      jest.spyOn(relatedPartyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedParty });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relatedParty }));
      saveSubject.complete();

      // THEN
      expect(relatedPartyFormService.getRelatedParty).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(relatedPartyService.update).toHaveBeenCalledWith(expect.objectContaining(relatedParty));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedParty>>();
      const relatedParty = { id: 'ABC' };
      jest.spyOn(relatedPartyFormService, 'getRelatedParty').mockReturnValue({ id: null });
      jest.spyOn(relatedPartyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedParty: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relatedParty }));
      saveSubject.complete();

      // THEN
      expect(relatedPartyFormService.getRelatedParty).toHaveBeenCalled();
      expect(relatedPartyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedParty>>();
      const relatedParty = { id: 'ABC' };
      jest.spyOn(relatedPartyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedParty });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(relatedPartyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
