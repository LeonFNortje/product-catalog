import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RelatedPlaceFormService } from './related-place-form.service';
import { RelatedPlaceService } from '../service/related-place.service';
import { IRelatedPlace } from '../related-place.model';

import { RelatedPlaceUpdateComponent } from './related-place-update.component';

describe('RelatedPlace Management Update Component', () => {
  let comp: RelatedPlaceUpdateComponent;
  let fixture: ComponentFixture<RelatedPlaceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let relatedPlaceFormService: RelatedPlaceFormService;
  let relatedPlaceService: RelatedPlaceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RelatedPlaceUpdateComponent],
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
      .overrideTemplate(RelatedPlaceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RelatedPlaceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    relatedPlaceFormService = TestBed.inject(RelatedPlaceFormService);
    relatedPlaceService = TestBed.inject(RelatedPlaceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const relatedPlace: IRelatedPlace = { id: 'CBA' };

      activatedRoute.data = of({ relatedPlace });
      comp.ngOnInit();

      expect(comp.relatedPlace).toEqual(relatedPlace);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedPlace>>();
      const relatedPlace = { id: 'ABC' };
      jest.spyOn(relatedPlaceFormService, 'getRelatedPlace').mockReturnValue(relatedPlace);
      jest.spyOn(relatedPlaceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedPlace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relatedPlace }));
      saveSubject.complete();

      // THEN
      expect(relatedPlaceFormService.getRelatedPlace).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(relatedPlaceService.update).toHaveBeenCalledWith(expect.objectContaining(relatedPlace));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedPlace>>();
      const relatedPlace = { id: 'ABC' };
      jest.spyOn(relatedPlaceFormService, 'getRelatedPlace').mockReturnValue({ id: null });
      jest.spyOn(relatedPlaceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedPlace: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relatedPlace }));
      saveSubject.complete();

      // THEN
      expect(relatedPlaceFormService.getRelatedPlace).toHaveBeenCalled();
      expect(relatedPlaceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelatedPlace>>();
      const relatedPlace = { id: 'ABC' };
      jest.spyOn(relatedPlaceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relatedPlace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(relatedPlaceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
