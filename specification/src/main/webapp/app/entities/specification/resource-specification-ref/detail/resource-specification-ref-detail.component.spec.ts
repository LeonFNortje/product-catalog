import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceSpecificationRefDetailComponent } from './resource-specification-ref-detail.component';

describe('ResourceSpecificationRef Management Detail Component', () => {
  let comp: ResourceSpecificationRefDetailComponent;
  let fixture: ComponentFixture<ResourceSpecificationRefDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResourceSpecificationRefDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resourceSpecificationRef: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ResourceSpecificationRefDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResourceSpecificationRefDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resourceSpecificationRef on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resourceSpecificationRef).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
