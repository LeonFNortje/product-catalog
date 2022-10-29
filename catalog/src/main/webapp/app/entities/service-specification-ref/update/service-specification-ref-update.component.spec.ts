import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ServiceSpecificationRefFormService } from './service-specification-ref-form.service';
import { ServiceSpecificationRefService } from '../service/service-specification-ref.service';
import { IServiceSpecificationRef } from '../service-specification-ref.model';

import { ServiceSpecificationRefUpdateComponent } from './service-specification-ref-update.component';

describe('ServiceSpecificationRef Management Update Component', () => {
  let comp: ServiceSpecificationRefUpdateComponent;
  let fixture: ComponentFixture<ServiceSpecificationRefUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviceSpecificationRefFormService: ServiceSpecificationRefFormService;
  let serviceSpecificationRefService: ServiceSpecificationRefService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ServiceSpecificationRefUpdateComponent],
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
      .overrideTemplate(ServiceSpecificationRefUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceSpecificationRefUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviceSpecificationRefFormService = TestBed.inject(ServiceSpecificationRefFormService);
    serviceSpecificationRefService = TestBed.inject(ServiceSpecificationRefService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const serviceSpecificationRef: IServiceSpecificationRef = { id: 'CBA' };

      activatedRoute.data = of({ serviceSpecificationRef });
      comp.ngOnInit();

      expect(comp.serviceSpecificationRef).toEqual(serviceSpecificationRef);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceSpecificationRef>>();
      const serviceSpecificationRef = { id: 'ABC' };
      jest.spyOn(serviceSpecificationRefFormService, 'getServiceSpecificationRef').mockReturnValue(serviceSpecificationRef);
      jest.spyOn(serviceSpecificationRefService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceSpecificationRef });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceSpecificationRef }));
      saveSubject.complete();

      // THEN
      expect(serviceSpecificationRefFormService.getServiceSpecificationRef).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviceSpecificationRefService.update).toHaveBeenCalledWith(expect.objectContaining(serviceSpecificationRef));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceSpecificationRef>>();
      const serviceSpecificationRef = { id: 'ABC' };
      jest.spyOn(serviceSpecificationRefFormService, 'getServiceSpecificationRef').mockReturnValue({ id: null });
      jest.spyOn(serviceSpecificationRefService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceSpecificationRef: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceSpecificationRef }));
      saveSubject.complete();

      // THEN
      expect(serviceSpecificationRefFormService.getServiceSpecificationRef).toHaveBeenCalled();
      expect(serviceSpecificationRefService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceSpecificationRef>>();
      const serviceSpecificationRef = { id: 'ABC' };
      jest.spyOn(serviceSpecificationRefService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceSpecificationRef });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviceSpecificationRefService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
