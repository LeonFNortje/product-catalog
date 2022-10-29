jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ProductSpecificationCharacteristicService } from '../service/product-specification-characteristic.service';

import { ProductSpecificationCharacteristicDeleteDialogComponent } from './product-specification-characteristic-delete-dialog.component';

describe('ProductSpecificationCharacteristic Management Delete Component', () => {
  let comp: ProductSpecificationCharacteristicDeleteDialogComponent;
  let fixture: ComponentFixture<ProductSpecificationCharacteristicDeleteDialogComponent>;
  let service: ProductSpecificationCharacteristicService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProductSpecificationCharacteristicDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ProductSpecificationCharacteristicDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSpecificationCharacteristicDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProductSpecificationCharacteristicService);
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
