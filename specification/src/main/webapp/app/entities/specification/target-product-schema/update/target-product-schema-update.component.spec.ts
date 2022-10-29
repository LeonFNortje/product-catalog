import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TargetProductSchemaFormService } from './target-product-schema-form.service';
import { TargetProductSchemaService } from '../service/target-product-schema.service';
import { ITargetProductSchema } from '../target-product-schema.model';

import { TargetProductSchemaUpdateComponent } from './target-product-schema-update.component';

describe('TargetProductSchema Management Update Component', () => {
  let comp: TargetProductSchemaUpdateComponent;
  let fixture: ComponentFixture<TargetProductSchemaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let targetProductSchemaFormService: TargetProductSchemaFormService;
  let targetProductSchemaService: TargetProductSchemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TargetProductSchemaUpdateComponent],
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
      .overrideTemplate(TargetProductSchemaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TargetProductSchemaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    targetProductSchemaFormService = TestBed.inject(TargetProductSchemaFormService);
    targetProductSchemaService = TestBed.inject(TargetProductSchemaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const targetProductSchema: ITargetProductSchema = { id: 456 };

      activatedRoute.data = of({ targetProductSchema });
      comp.ngOnInit();

      expect(comp.targetProductSchema).toEqual(targetProductSchema);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITargetProductSchema>>();
      const targetProductSchema = { id: 123 };
      jest.spyOn(targetProductSchemaFormService, 'getTargetProductSchema').mockReturnValue(targetProductSchema);
      jest.spyOn(targetProductSchemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetProductSchema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: targetProductSchema }));
      saveSubject.complete();

      // THEN
      expect(targetProductSchemaFormService.getTargetProductSchema).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(targetProductSchemaService.update).toHaveBeenCalledWith(expect.objectContaining(targetProductSchema));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITargetProductSchema>>();
      const targetProductSchema = { id: 123 };
      jest.spyOn(targetProductSchemaFormService, 'getTargetProductSchema').mockReturnValue({ id: null });
      jest.spyOn(targetProductSchemaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetProductSchema: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: targetProductSchema }));
      saveSubject.complete();

      // THEN
      expect(targetProductSchemaFormService.getTargetProductSchema).toHaveBeenCalled();
      expect(targetProductSchemaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITargetProductSchema>>();
      const targetProductSchema = { id: 123 };
      jest.spyOn(targetProductSchemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetProductSchema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(targetProductSchemaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
