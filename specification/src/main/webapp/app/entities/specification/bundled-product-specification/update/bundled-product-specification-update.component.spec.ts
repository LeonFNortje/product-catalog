import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BundledProductSpecificationFormService } from './bundled-product-specification-form.service';
import { BundledProductSpecificationService } from '../service/bundled-product-specification.service';
import { IBundledProductSpecification } from '../bundled-product-specification.model';

import { BundledProductSpecificationUpdateComponent } from './bundled-product-specification-update.component';

describe('BundledProductSpecification Management Update Component', () => {
  let comp: BundledProductSpecificationUpdateComponent;
  let fixture: ComponentFixture<BundledProductSpecificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bundledProductSpecificationFormService: BundledProductSpecificationFormService;
  let bundledProductSpecificationService: BundledProductSpecificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BundledProductSpecificationUpdateComponent],
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
      .overrideTemplate(BundledProductSpecificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BundledProductSpecificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bundledProductSpecificationFormService = TestBed.inject(BundledProductSpecificationFormService);
    bundledProductSpecificationService = TestBed.inject(BundledProductSpecificationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bundledProductSpecification: IBundledProductSpecification = { id: 'CBA' };

      activatedRoute.data = of({ bundledProductSpecification });
      comp.ngOnInit();

      expect(comp.bundledProductSpecification).toEqual(bundledProductSpecification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBundledProductSpecification>>();
      const bundledProductSpecification = { id: 'ABC' };
      jest.spyOn(bundledProductSpecificationFormService, 'getBundledProductSpecification').mockReturnValue(bundledProductSpecification);
      jest.spyOn(bundledProductSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bundledProductSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bundledProductSpecification }));
      saveSubject.complete();

      // THEN
      expect(bundledProductSpecificationFormService.getBundledProductSpecification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bundledProductSpecificationService.update).toHaveBeenCalledWith(expect.objectContaining(bundledProductSpecification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBundledProductSpecification>>();
      const bundledProductSpecification = { id: 'ABC' };
      jest.spyOn(bundledProductSpecificationFormService, 'getBundledProductSpecification').mockReturnValue({ id: null });
      jest.spyOn(bundledProductSpecificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bundledProductSpecification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bundledProductSpecification }));
      saveSubject.complete();

      // THEN
      expect(bundledProductSpecificationFormService.getBundledProductSpecification).toHaveBeenCalled();
      expect(bundledProductSpecificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBundledProductSpecification>>();
      const bundledProductSpecification = { id: 'ABC' };
      jest.spyOn(bundledProductSpecificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bundledProductSpecification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bundledProductSpecificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
