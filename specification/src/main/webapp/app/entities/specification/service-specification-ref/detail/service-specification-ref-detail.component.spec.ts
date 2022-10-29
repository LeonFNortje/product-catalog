import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiceSpecificationRefDetailComponent } from './service-specification-ref-detail.component';

describe('ServiceSpecificationRef Management Detail Component', () => {
  let comp: ServiceSpecificationRefDetailComponent;
  let fixture: ComponentFixture<ServiceSpecificationRefDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServiceSpecificationRefDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ serviceSpecificationRef: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ServiceSpecificationRefDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServiceSpecificationRefDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load serviceSpecificationRef on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.serviceSpecificationRef).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
