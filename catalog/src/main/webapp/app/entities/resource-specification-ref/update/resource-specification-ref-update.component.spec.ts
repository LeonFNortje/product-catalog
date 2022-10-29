import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResourceSpecificationRefFormService } from './resource-specification-ref-form.service';
import { ResourceSpecificationRefService } from '../service/resource-specification-ref.service';
import { IResourceSpecificationRef } from '../resource-specification-ref.model';

import { ResourceSpecificationRefUpdateComponent } from './resource-specification-ref-update.component';

describe('ResourceSpecificationRef Management Update Component', () => {
  let comp: ResourceSpecificationRefUpdateComponent;
  let fixture: ComponentFixture<ResourceSpecificationRefUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resourceSpecificationRefFormService: ResourceSpecificationRefFormService;
  let resourceSpecificationRefService: ResourceSpecificationRefService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResourceSpecificationRefUpdateComponent],
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
      .overrideTemplate(ResourceSpecificationRefUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResourceSpecificationRefUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resourceSpecificationRefFormService = TestBed.inject(ResourceSpecificationRefFormService);
    resourceSpecificationRefService = TestBed.inject(ResourceSpecificationRefService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resourceSpecificationRef: IResourceSpecificationRef = { id: 'CBA' };

      activatedRoute.data = of({ resourceSpecificationRef });
      comp.ngOnInit();

      expect(comp.resourceSpecificationRef).toEqual(resourceSpecificationRef);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResourceSpecificationRef>>();
      const resourceSpecificationRef = { id: 'ABC' };
      jest.spyOn(resourceSpecificationRefFormService, 'getResourceSpecificationRef').mockReturnValue(resourceSpecificationRef);
      jest.spyOn(resourceSpecificationRefService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resourceSpecificationRef });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resourceSpecificationRef }));
      saveSubject.complete();

      // THEN
      expect(resourceSpecificationRefFormService.getResourceSpecificationRef).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resourceSpecificationRefService.update).toHaveBeenCalledWith(expect.objectContaining(resourceSpecificationRef));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResourceSpecificationRef>>();
      const resourceSpecificationRef = { id: 'ABC' };
      jest.spyOn(resourceSpecificationRefFormService, 'getResourceSpecificationRef').mockReturnValue({ id: null });
      jest.spyOn(resourceSpecificationRefService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resourceSpecificationRef: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resourceSpecificationRef }));
      saveSubject.complete();

      // THEN
      expect(resourceSpecificationRefFormService.getResourceSpecificationRef).toHaveBeenCalled();
      expect(resourceSpecificationRefService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResourceSpecificationRef>>();
      const resourceSpecificationRef = { id: 'ABC' };
      jest.spyOn(resourceSpecificationRefService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resourceSpecificationRef });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resourceSpecificationRefService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
