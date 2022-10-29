jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ServiceSpecificationRefService } from '../service/service-specification-ref.service';

import { ServiceSpecificationRefDeleteDialogComponent } from './service-specification-ref-delete-dialog.component';

describe('ServiceSpecificationRef Management Delete Component', () => {
  let comp: ServiceSpecificationRefDeleteDialogComponent;
  let fixture: ComponentFixture<ServiceSpecificationRefDeleteDialogComponent>;
  let service: ServiceSpecificationRefService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ServiceSpecificationRefDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ServiceSpecificationRefDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServiceSpecificationRefDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServiceSpecificationRefService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
