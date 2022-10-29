import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AttachmentRefOrValueFormService } from './attachment-ref-or-value-form.service';
import { AttachmentRefOrValueService } from '../service/attachment-ref-or-value.service';
import { IAttachmentRefOrValue } from '../attachment-ref-or-value.model';

import { AttachmentRefOrValueUpdateComponent } from './attachment-ref-or-value-update.component';

describe('AttachmentRefOrValue Management Update Component', () => {
  let comp: AttachmentRefOrValueUpdateComponent;
  let fixture: ComponentFixture<AttachmentRefOrValueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let attachmentRefOrValueFormService: AttachmentRefOrValueFormService;
  let attachmentRefOrValueService: AttachmentRefOrValueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AttachmentRefOrValueUpdateComponent],
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
      .overrideTemplate(AttachmentRefOrValueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AttachmentRefOrValueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    attachmentRefOrValueFormService = TestBed.inject(AttachmentRefOrValueFormService);
    attachmentRefOrValueService = TestBed.inject(AttachmentRefOrValueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const attachmentRefOrValue: IAttachmentRefOrValue = { id: 'CBA' };

      activatedRoute.data = of({ attachmentRefOrValue });
      comp.ngOnInit();

      expect(comp.attachmentRefOrValue).toEqual(attachmentRefOrValue);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachmentRefOrValue>>();
      const attachmentRefOrValue = { id: 'ABC' };
      jest.spyOn(attachmentRefOrValueFormService, 'getAttachmentRefOrValue').mockReturnValue(attachmentRefOrValue);
      jest.spyOn(attachmentRefOrValueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachmentRefOrValue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachmentRefOrValue }));
      saveSubject.complete();

      // THEN
      expect(attachmentRefOrValueFormService.getAttachmentRefOrValue).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(attachmentRefOrValueService.update).toHaveBeenCalledWith(expect.objectContaining(attachmentRefOrValue));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachmentRefOrValue>>();
      const attachmentRefOrValue = { id: 'ABC' };
      jest.spyOn(attachmentRefOrValueFormService, 'getAttachmentRefOrValue').mockReturnValue({ id: null });
      jest.spyOn(attachmentRefOrValueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachmentRefOrValue: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachmentRefOrValue }));
      saveSubject.complete();

      // THEN
      expect(attachmentRefOrValueFormService.getAttachmentRefOrValue).toHaveBeenCalled();
      expect(attachmentRefOrValueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachmentRefOrValue>>();
      const attachmentRefOrValue = { id: 'ABC' };
      jest.spyOn(attachmentRefOrValueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachmentRefOrValue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(attachmentRefOrValueService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
